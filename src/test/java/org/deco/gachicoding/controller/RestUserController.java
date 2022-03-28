package org.deco.gachicoding.controller;

import lombok.RequiredArgsConstructor;
import org.deco.gachicoding.domain.user.User;
import org.deco.gachicoding.dto.jwt.JwtRequestDto;
import org.deco.gachicoding.dto.jwt.JwtResponseDto;
import org.deco.gachicoding.dto.social.SocialSaveRequestDto;
import org.deco.gachicoding.dto.user.UserResponseDto;
import org.deco.gachicoding.dto.user.UserSaveRequestDto;
import org.deco.gachicoding.dto.user.UserUpdateRequestDto;
import org.deco.gachicoding.service.social.SocialService;
import org.deco.gachicoding.service.user.UserService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class RestUserController {

    private final UserService userService;

    private final SocialService socialService;

    @PostMapping("/user/login")
    public JwtResponseDto login(@RequestBody JwtRequestDto dto) throws Exception {
        return userService.login(dto);
    }

    @GetMapping("/user/{idx}")
    public UserResponseDto getUser(@PathVariable Long idx){
//        return userService.getUser(idx);
        return null;
    }

    @PostMapping("/user")
    public Long registerUser(@Valid @RequestBody UserSaveRequestDto dto) {
        return userService.registerUser(dto);
    }

    @PutMapping("/user/{idx}")
    public Long updateUser(@PathVariable Long idx, @RequestBody UserUpdateRequestDto dto){
        return userService.updateUser(idx, dto);
    }

    @DeleteMapping("/user/{idx}")
    public Long deleteUser(@PathVariable Long idx){
        return userService.deleteUser(idx);
    }

    @GetMapping("/user/kakaoLogin")
    public JwtResponseDto kakaoUserLogin(String code) throws Exception {
        System.out.println("kakaoCode" + code);

        Long idx;

        String accessToken = socialService.getKakaoAccessToken(code);
        SocialSaveRequestDto socialSaveRequestDto = socialService.getKakaoUserInfo(accessToken);

        // 회원 확인
        Optional<User> user = userService.getUserByUserEmail(socialSaveRequestDto.getSocial_id());

        JwtRequestDto jwtRequestDto = new JwtRequestDto();

        jwtRequestDto.setEmail(socialSaveRequestDto.getSocial_id());

        // 카카오 소셜 인증이 없으면
        if(socialService.getSocialTypeAndEmail(socialSaveRequestDto).isEmpty()) {

            // 같은 이메일로 가입된 회원이 없으면
            if (user.isEmpty()) {
                // 유저 회원 가입
                UserSaveRequestDto userSaveRequestDto = UserSaveRequestDto.builder()
                                                        .userName(socialSaveRequestDto.getName())
                                                        .userEmail(socialSaveRequestDto.getSocial_id())
                                                        .userPassword("a123456789a")    // -> 정해야함 암호화된 문자열을 쓰든, 비밀번호 확인 못하게 고정된 키 값을 만들어 두든
//                                                        .userRole(UserRole.USER)
                                                        .build();

                idx = userService.registerUser(userSaveRequestDto);

                jwtRequestDto.setPassword("a123456789a");

                System.out.println("신규 유저 소셜 회원 가입 + 로그인 입니다.");
            } else {
                idx = user.get().getUserIdx();

                jwtRequestDto.setPassword(user.get().getUserPassword());

                System.out.println("기존 유저 소셜 인증 + 로그인 입니다.");
            }
            // 유저 idx를 몰랐기 때문에 지금 set
            socialSaveRequestDto.setUser_idx(idx);
            socialService.registerSocial(socialSaveRequestDto);
        } 
        // 있으면 로그인 처리(이메일 만을 사용해야함)
        else {
            jwtRequestDto.setPassword(user.get().getUserPassword());
            System.out.println("기존 회원 로그인 입니다." + user.get().getUserPassword());
        }

        // => email - socialId, password - 유저 검색을 통해 알아야함
        return userService.login(jwtRequestDto);
    }

}