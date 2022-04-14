package org.deco.gachicoding.service.social.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.deco.gachicoding.domain.social.Social;
import org.deco.gachicoding.domain.social.SocialRepository;
import org.deco.gachicoding.dto.social.SocialSaveRequestDto;
import org.deco.gachicoding.service.social.SocialService;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SocialServiceImpl implements SocialService {

    private final SocialRepository socialAuthRepository;

    // 소셜 인증 테이블 input
    @Override
    public Long registerSocial(SocialSaveRequestDto dto) {
        System.out.println("Social Save 수행");

        Long idx = socialAuthRepository.save(dto.toEntity()).getSocialIdx();

        return idx;
    }

    // SocialType(kakao, google, github) SocialId(Email)로 회원 검색
    public Optional<Social> getSocialTypeAndEmail(SocialSaveRequestDto dto) {
        return socialAuthRepository.findBySocialTypeAndSocialId(dto.getSocialType(), dto.getSocialId());
    }

    // 카카오 엑세스 토큰 가져오기
    public String getKakaoAccessToken(String code) {
        String accessToken = "";
        String refreshToken = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            // POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=7830dfe0c664f6c2376155c0f0c5e286");
            sb.append("&redirect_uri=http://localhost:8080/api/user/kakaoLogin&response_type=code");
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();

            // 결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);
            // 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            // Gson 라이브러리에 포함된 클래스에 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            accessToken = element.getAsJsonObject().get("access_token").getAsString();
            refreshToken = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("accessToken : " + accessToken);
            System.out.println("refreshToken : " + refreshToken);

            br.close();
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return accessToken;
    }

    // 엑세스 토큰 이용해 유저 정보 가져오기
    public SocialSaveRequestDto getKakaoUserInfo(String token) throws Exception {
        String reqURL = "https://kapi.kakao.com/v2/user/me";

        SocialSaveRequestDto social = null;

        // access_token을 이용하여 사용자 정보 조회
        try {
            social = new SocialSaveRequestDto();
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + token);    // 전송할 header 작성, access_token전송
            conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

            // 결과 코드가 200이면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("response code : " + responseCode);

            // 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            // Gson 라이브러리로 Json 파싱
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            int id = element.getAsJsonObject().get("id").getAsInt();
            boolean hasEmail = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("has_email").getAsBoolean();
            String nickname = element.getAsJsonObject().get("properties").getAsJsonObject().get("nickname").getAsString();
            String email = "";
            if(hasEmail) {
                email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
            }

            System.out.println("id : " + id);
            System.out.println("nickname : " + nickname);
            System.out.println("email : " + email);

            social.setUserName(nickname);
            social.setSocialId(email);
            // 패스워드 어케할지 고민
            social.setSocialType("kakao");

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return social;
    }
}
