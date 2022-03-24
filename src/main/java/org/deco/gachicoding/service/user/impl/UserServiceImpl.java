package org.deco.gachicoding.service.user.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.deco.gachicoding.config.jwt.JwtTokenProvider;
import org.deco.gachicoding.domain.user.User;
import org.deco.gachicoding.domain.user.UserRepository;
import org.deco.gachicoding.domain.utils.email.ConfirmationToken;
import org.deco.gachicoding.dto.user.*;
import org.deco.gachicoding.service.user.UserService;
import org.deco.gachicoding.service.email.ConfirmationTokenService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ConfirmationTokenService confirmationTokenService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    @Override
    public UserResponseDto getUser(Long idx) {

        User user = userRepository.findById(idx)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다. 회원 번호 = " + idx));

        return new UserResponseDto(user);
    }

    @Transactional
    @Override
    public JwtResponseDto login(JwtRequestDto request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            return createJwtToken(authentication);
            // BadCredentialsException - 스프링 시큐리티 에서 아이디 또는 비밀번호가 틀렸을 경우 나오는 예외
        } catch (BadCredentialsException e) {
            e.printStackTrace();
            return new JwtResponseDto("아이디 또는 비밀번호를 확인해 주세요.");
        }
    }

    private JwtResponseDto createJwtToken(Authentication authentication) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        String token = jwtTokenProvider.generateToken(principal);
        return new JwtResponseDto(token);
    }

    // @Transactional - 아이디 중복 시 - Transaction silently rolled back because it has been marked as rollback-only 발생
    // 이유 트랜잭션은 재사용 될수 없다.
    // save하면서 같은 이메일이 있으면 예외를 발생, 예외 발생 시 기본 값으로 들어있는 롤백이 true가 됨
    // save가 끝나고 나오면서 registerUser로 돌아 왔을때 @Transactional어노테이션이 있으면
    // 커밋을 앞에서 예외를 잡았기 때문에 문제 없다고 판단, 커밋을 실행한다. 하지만 roll-back only**이 마킹되어 있어 **롤백함.
    // 에러 발생 - 이와 관련해선 좀 딥한 부분인거 같아서 공부를 좀 더 해야할 거 같음 + 트러블 슈팅으로 넣으면 좋을 듯
    // @Transactional 사용도 신중해야 할 필요가 있을 듯
//    @Override
//    public Long registerUser(UserSaveRequestDto dto) {
//
//        dto.encryptPassword(passwordEncoder);
//
//        try {
//            Long idx = userRepository.save(dto.toEntity()).getIdx();
//
//            System.out.println("User Save 수행");
//
//            // 이메일 인증 기능 분리 필요
//            confirmationTokenService.createEmailConfirmationToken(dto.getEmail());
//
//            return idx;
//            // ConstraintViolationException - 디비와 매핑되어있는 Entity 객체의 Key가 중복으로 save 될 때 발생하는 예외
//        } catch (DataIntegrityViolationException e) {
////            e.printStackTrace();
//            System.out.println(dto.getEmail() + " : User Save 실패\n 중복된 아이디 입니다.");
//            return Long.valueOf(-100);
//        }
//    }

    @Override
    public Long registerUser(UserSaveRequestDto dto) {

        dto.encryptPassword(passwordEncoder);

        if(getUserByEmail(dto.getEmail()).get() == null) {
            System.out.println("User Save 수행");

            Long idx = userRepository.save(dto.toEntity()).getIdx();

            // 이메일 인증 기능 분리 필요
            confirmationTokenService.createEmailConfirmationToken(dto.getEmail());

            return idx;
        } else {
            System.out.println(dto.getEmail() + " : User Save 실패\n 중복된 아이디 입니다.");
            return Long.valueOf(-100);
        }
    }

    /**
     * 이메일 인증 로직
     * @param token
     */
    @Transactional
    @Override
    public void confirmEmail(String token) {
        ConfirmationToken findConfirmationToken = confirmationTokenService.findByIdExpirationDateAfterAndExpired(token);
        Optional<User> findUserInfo = getUserByEmail(findConfirmationToken.getEmail());
        findConfirmationToken.useToken();   // 토큰 만료 로직을 구현해주면 된다. ex) expired 값을 true 로 변경
//        findUserInfo.emailVerifiedSuccess();    // 유저의 이메일 인증 값 변경 로직을 구현해 주면 된다. ex) emailVerified 값을 true로 변경
    }

    @Transactional
    @Override
    public Long updateUser(Long idx, UserUpdateResponseDto dto) {

        User user = userRepository.findById(idx)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다. 회원 번호 = " + idx));

        user.update(dto.getName(), dto.getEmail(), dto.getPassword(), dto.getActivated(), dto.getRole());

        return idx;
    }

    @Transactional
    @Override
    public Long deleteUser(Long idx) {
        userRepository.deleteById(idx);
        return idx;
    }

    // 서비스에 만들어 놓은 카카오 관련 메서드는 여기 밖에 쓰이지 않을거 같아서 인터페이스에 정의 안함
    // 근데 그렇게 하는건지는 정확히 모르겠음 난중에 얘기해보면 좋을듯
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
            sb.append("&redirect_uri=http://localhost:8080/api/user/kakao&response_type=code");
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
    public void getKakaoUserInfo(String token) throws Exception {
        String reqURL = "https://kapi.kakao.com/v2/user/me";

        // access_token을 이용하여 사용자 정보 조회
        try {
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
            String email = "";
            if(hasEmail) {
                email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
            }

            System.out.println("id : " + id);
            System.out.println("email : " + email);

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}