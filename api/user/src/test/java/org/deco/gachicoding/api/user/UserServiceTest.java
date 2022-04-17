package org.deco.gachicoding.api.user;

import org.deco.gachicoding.api.user.dto.*;
import org.deco.gachicoding.api.user.service.UserService;
import org.deco.gachicoding.core.common.domain.User;
import org.deco.gachicoding.core.common.domain.UserRole;
import org.deco.gachicoding.infrastructure.web.GachicodingWebApplication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

// 서비스 테스트에서 비즈니스 로직에서 발생할 수 있는 예외 상황의 테스트를 진행한다
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = GachicodingWebApplication.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    Long userIdx;

    @BeforeEach
    void before() {
        UserSaveRequestDto userSaveRequestDto = new UserSaveRequestDto();
        userSaveRequestDto.setUserName("test Name");
        userSaveRequestDto.setUserEmail("test@test.com");
        userSaveRequestDto.setUserPassword("1234");
        userSaveRequestDto.setUserNick("test Nick");

        userIdx = userService.registerUser(userSaveRequestDto);
    }

    @AfterEach
    void after() {
        if (userIdx != null) {
            userService.deleteUser(this.userIdx);
        }
        userIdx = null;
    }

    @Test
    @DisplayName("UserServiceTest - 회원가입 성공 스프링 시큐리티")
    void 회원가입_성공() {
        Optional<User> user = userService.getUserByUserEmail("test@test.com");

        assertEquals(user.get().getUserEmail(), "test@test.com");
    }

    @Test
    @DisplayName("UserServiceTest - 회원가입 실패 중복된 이메일")
    void 회원가입_실패_중복된_이메일() {
        // Given
        UserSaveRequestDto userSaveRequestDto = new UserSaveRequestDto();
        userSaveRequestDto.setUserEmail("test@test.com");
        userSaveRequestDto.setUserName("test name");
        userSaveRequestDto.setUserPassword("1234");
        userSaveRequestDto.setUserNick("test nick");

        // When & Then
        // 중복된 이메일로 회원가입 할 경우 예외발생하는지 체크
        assertThrows(DataIntegrityViolationException.class, () -> {
            // 이미 등록된 이메일로 한번 더 회원가입
            userService.registerUser(userSaveRequestDto);
        });
    }
    
    @Test
    @DisplayName("UserServiceTest - 로그인 성공 JWT")
    void 로그인_성공() {
        // Given
        JwtRequestDto requestDto = new JwtRequestDto();
        requestDto.setEmail("test@test.com");
        requestDto.setPassword("1234");

        // When
        JwtResponseDto token = userService.login(requestDto);

        // Then
        System.out.println(token.getAccessToken());
    }

    @Test
    @DisplayName("UserServiceTest - 로그인 실패 잘못된 이메일")
    void 로그인_실패_잘못된_이메일() {
        // Given
        JwtRequestDto requestDto = new JwtRequestDto();
        requestDto.setEmail("failTestEmail@test.com");
        requestDto.setPassword("1234");

        // When
        JwtResponseDto token = userService.login(requestDto);

        // Then
        assertEquals(token.getAccessToken(), "아이디 또는 비밀번호를 확인해 주세요.");
    }

    @Test
    @DisplayName("UserServiceTest - 로그인 실패 잘못된 패스워드")
    void 로그인_실패_잘못된_패스워드() {
        // Given
        JwtRequestDto requestDto = new JwtRequestDto();
        requestDto.setEmail("test@test.com");
        requestDto.setPassword("failTestPassword");

        // When
        JwtResponseDto token = userService.login(requestDto);

        // Then
        assertEquals(token.getAccessToken(), "아이디 또는 비밀번호를 확인해 주세요.");
    }

    @Test
    @DisplayName("UserServiceTest - 유저 프로필 정보 수정")
    public void 유저정보_수정() {
        UserUpdateRequestDto dto = UserUpdateRequestDto.builder()
                .userNick("수정된 별명")
                .userPassword("4567")
                .userRole(UserRole.GUEST)
                .userPicture("수정된 프로필 사진")
                .build();

        userService.updateUser(userIdx, dto);

        User updateUser = userService.getUserByUserEmail("test@test.com").get();

        assertEquals("수정된 별명", updateUser.getUserNick());
    }

    @Test
    @DisplayName("UserServiceTest - 유저 비활성화")
    public void 유저_비활성화() {
        userService.disableUser(userIdx);

        User disableUser = userService.getUserByUserEmail("test@test.com").get();

        assertEquals(false, disableUser.isUserActivated());
    }

    @Test
    @DisplayName("UserServiceTest - 유저 활성화")
    public void 유저_활성화() {
        userService.enableUser(userIdx);

        User enableUser = userService.getUserByUserEmail("test@test.com").get();

        assertEquals(true, enableUser.isUserActivated());
    }

    @Test
    @DisplayName("UserServiceTest - 유저 삭제")
    public void 유저_삭제() {
        userService.deleteUser(userIdx);
        assertTrue(userService.getUserByUserEmail("test@test.com").isEmpty());
        userIdx = null;
    }
}
