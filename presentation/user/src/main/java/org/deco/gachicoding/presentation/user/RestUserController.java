package org.deco.gachicoding.presentation.user;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.deco.gachicoding.api.user.dto.*;
import org.deco.gachicoding.api.user.service.SocialService;
import org.deco.gachicoding.api.user.service.UserService;
import org.deco.gachicoding.core.common.domain.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class RestUserController {

    private final UserService userService;

    private final SocialService socialService;

    @ApiOperation(value = "로그인")
    @PostMapping("/user/login")
    public JwtResponseDto login(@RequestBody JwtRequestDto dto) throws Exception {
        return userService.login(dto);
    }

    @ApiOperation(value = "회원 가입")
    @PostMapping("/user")
    public Long registerUser(@Valid @RequestBody UserSaveRequestDto dto) {
        return userService.registerUser(dto);
    }

    @ApiOperation(value = "회원정보 수정")
    @PutMapping("/user/update/{idx}")
    public UserResponseDto updateUser(@PathVariable Long idx,@RequestBody UserUpdateRequestDto dto){
        return userService.updateUser(idx, dto);
    }

    @ApiOperation(value = "회원 비활성화")
    @PutMapping("/user/disable/{idx}")
    public void disableUser(@PathVariable Long idx){
        userService.disableUser(idx);
    }

    @ApiOperation(value = "회원 활성화")
    @PutMapping("/user/enable/{idx}")
    public void enableUser(@PathVariable Long idx){
        userService.enableUser(idx);
    }

    @ApiOperation(value = "회원 삭제")
    @DeleteMapping("/user/{idx}")
    public void deleteUser(@PathVariable Long idx){ userService.deleteUser(idx);
    }

    // 기본적인 흐름만 구현 리팩토링 필요
    @ApiOperation(value = "카카오 로그인")
    @GetMapping("/user/kakaoLogin")
    public JwtResponseDto kakaoUserLogin(String code) throws Exception {
        System.out.println("kakaoCode" + code);

        Long idx;

        String accessToken = socialService.getKakaoAccessToken(code);
        SocialSaveRequestDto socialSaveRequestDto = socialService.getKakaoUserInfo(accessToken);

        // 회원 확인
        Optional<User> user = userService.getUserByUserEmail(socialSaveRequestDto.getSocialId());

        JwtRequestDto jwtRequestDto = new JwtRequestDto();

        jwtRequestDto.setEmail(socialSaveRequestDto.getSocialId());

        // 카카오 소셜 인증이 없으면
        if(socialService.getSocialTypeAndEmail(socialSaveRequestDto).isEmpty()) {

            // 같은 이메일로 가입된 회원이 없으면
            if (user.isEmpty()) {
                // 유저 회원 가입
                UserSaveRequestDto userSaveRequestDto = UserSaveRequestDto.builder()
                                                        .userName(socialSaveRequestDto.getUserName())
                                                        .userEmail(socialSaveRequestDto.getSocialId())
                                                        .userPassword("a123456789a")    // -> 정해야함 암호화된 문자열을 쓰든, 비밀번호 확인 못하게 고정된 키 값을 만들어 두든
                                                        .userNick(socialSaveRequestDto.getUserName())   // -> 따로 닉네임을 받든(이쪽이 좋을듯 -> 그럼 null값으로 닉네임 넣어두고 업데이트 하는 형태로 가야할 듯), 초기 닉네임을 이름으로 하든
                                                        .userPicture("userPicture")     // -> 프로필 사진, 수정해야됨
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
            socialSaveRequestDto.setUserIdx(idx);
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