package org.deco.gachicoding.controller;

import lombok.RequiredArgsConstructor;
import org.deco.gachicoding.domain.user.Role;
import org.deco.gachicoding.domain.user.SocialAuth;
import org.deco.gachicoding.domain.user.User;
import org.deco.gachicoding.dto.user.*;
import org.deco.gachicoding.service.user.UserService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class RestUserController {

    private final UserService userService;

    @PostMapping("/user/login")
    public JwtResponseDto login(@RequestBody JwtRequestDto dto) throws Exception {
        return userService.login(dto);
    }

    @GetMapping("/user/{idx}")
    public UserResponseDto getUser(@PathVariable Long idx){
        return userService.getUser(idx);
    }

    @PostMapping("/user")
    public Long registerUser(@Valid @RequestBody UserSaveRequestDto dto) {
        return userService.registerUser(dto);
    }

    @PutMapping("/user/{idx}")
    public Long updateUser(@PathVariable Long idx,@RequestBody UserUpdateResponseDto dto){
        return userService.updateUser(idx, dto);
    }

    @DeleteMapping("/user/{idx}")
    public Long deleteUser(@PathVariable Long idx){
        return userService.deleteUser(idx);
    }

    @GetMapping("/user/kakao")
    public String kakaoUserLogin(String code) throws Exception {
        System.out.println("kakaoCode" + code);

        Long idx;

        String accessToken = userService.getKakaoAccessToken(code);
        SocialSaveRequestDto socialSaveRequestDto = userService.getKakaoUserInfo(accessToken);

        // 카카오 소셜 인증이 없으면
        if(userService.getSocialTypeAndEmail(socialSaveRequestDto).isEmpty()) {
            // 회원 확인
            Optional<User> user = userService.getUserByEmail(socialSaveRequestDto.getSocial_id());

            // 같은 이메일로 가입된 회원이 없으면
            if (user.isEmpty()) {
                // 유저 회원 가입
                UserSaveRequestDto userSaveRequestDto = UserSaveRequestDto.builder()
                                                        .name(socialSaveRequestDto.getName())
                                                        .email(socialSaveRequestDto.getSocial_id())
                                                        .password("a123456789a")    // -> 정해야함 암호화된 문자열을 쓰든, 비밀번호 확인 못하게 고정된 키 값을 만들어 두든
                                                        .role(Role.USER)
                                                        .build();

                idx = userService.registerUser(userSaveRequestDto);

                System.out.println("신규 유저 소셜 회원 가입 + 로그인 입니다.");
            } else {
                idx = user.get().getIdx();

                System.out.println("기존 유저 소셜 인증 + 로그인 입니다.");
            }
            // 유저 idx를 몰랐기 때문에 지금 set
            socialSaveRequestDto.setUser_idx(idx);
            userService.registerSocial(socialSaveRequestDto);
        } 
        // 있으면 로그인 처리(이메일 만을 사용해야함)
        else {
            System.out.println("기존 회원 로그인 입니다.");
        }

        return "카카오 로그인 입니다.";
    }

}