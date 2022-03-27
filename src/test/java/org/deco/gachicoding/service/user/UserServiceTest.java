package org.deco.gachicoding.service.user;

import org.deco.gachicoding.domain.user.User;
import org.deco.gachicoding.dto.user.JwtRequestDto;
import org.deco.gachicoding.dto.user.JwtResponseDto;
import org.deco.gachicoding.dto.user.UserSaveRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

// 서비스 테스트에서 비즈니스 로직에서 발생할 수 있는 예외 상황의 테스트를 진행한다
//@ExtendWith(SpringExtension.class)

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserServiceTest {

    @Autowired
    UserService userService;

    @AfterEach
    void cleanUp() {

    }

    @Test
    void 이메일로_유저정보_가져오기_해당유저존재() {
        String email = "test@test.com";
        Optional<User> user = userService.getUserByEmail(email);
        assertTrue(user.isPresent());
    }

    @Test
    void 이메일로_유저정보_가져오기_해당유저없음() {
        String email = "inhan1009@naver.com";
        Optional<User> user = userService.getUserByEmail(email);
        assertTrue(user.isEmpty());
    }


    @Test
    void 중복이메일_존재() {

        String email = "test@test.com";
        assertTrue(userService.existDuplicateEmail(email));
    }

    @Test
    void 중복이메일_존재하지_않음() {

        String email = "inhan1009@naver.com";
        assertFalse(userService.existDuplicateEmail(email));
    }

    @Test
    void 회원가입_성공() {

        String email = "inhan1009@naver.com";
        String name = "김인환";
        String password = "1234";

        UserSaveRequestDto dto = new UserSaveRequestDto(name, email, password);

        userService.registerUser(dto);

        Optional<User> user = userService.getUserByEmail(email);

        assertEquals(email, user.get().getUserEmail());
        assertEquals(name, user.get().getUserName());
        assertEquals(password, user.get().getUserPassword());
    }

    @Test
    void 회원가입_실패_이메일_중복인_경우() {

    }

    @Test
    @DisplayName("UserService - JWT 로그인 테스트")
    void login() {
        // Given
        JwtRequestDto requestDto = new JwtRequestDto();
        requestDto.setEmail("ay9564@naver.com");
        requestDto.setPassword("ay789456");

        // When
        JwtResponseDto token = userService.login(requestDto);

        // Then
        System.out.println(token.getAccessToken());
    }

    @Test
    @DisplayName("UserService - JWT 아이디 없음 로그인 테스트")
    void notFindEmailLogin() {
        // Given
        JwtRequestDto requestDto = new JwtRequestDto();
        requestDto.setEmail("ay7871@naver.com");
        requestDto.setPassword("ay789456");

        // When
        JwtResponseDto token = userService.login(requestDto);

        // Then
        assertEquals(token.getAccessToken(), "아이디 또는 비밀번호를 확인해 주세요.");
    }

    @Test
    @DisplayName("UserService - JWT 비밀번호 틀림 로그인 테스트")
    void notFindPasswordLogin() {
        // Given
        JwtRequestDto requestDto = new JwtRequestDto();
        requestDto.setEmail("ay7871@naver.com");
        requestDto.setPassword("인환이바보");

        // When
        JwtResponseDto token = userService.login(requestDto);

        // Then
        assertEquals(token, "아이디 또는 비밀번호를 확인해 주세요.");
    }

    @Test
    @DisplayName("UserService - 회원가입 테스트")
    void JoinUser() {
        // Given
        /*
        UserSaveRequestDto userSaveRequestDto = new UserSaveRequestDto();
        userSaveRequestDto.setEmail("ay9564@naver.com");
        userSaveRequestDto.setName("서영준");
        userSaveRequestDto.setPassword("ay789456");

        // When
        Long idx = userService.registerUser(userSaveRequestDto);
        UserResponseDto user = userService.getUser(idx);

        // Then
        assertEquals(user.getEmail(), userSaveRequestDto.getEmail());
        */
    }

    @Test
    @DisplayName("UserService - 중복 아이디 회원가입 테스트")
    void duplicationEmailJoin() {
        // Given
        UserSaveRequestDto userSaveRequestDto1 = new UserSaveRequestDto();
        userSaveRequestDto1.setEmail("ay9564@naver.com");
        userSaveRequestDto1.setName("서영준");
        userSaveRequestDto1.setPassword("ay789456");

        // When
//        Long exception_code = userService.registerUser(userSaveRequestDto1);

        // exception_code(난중에 다시 정하자) : -100, message : "중복된 아이디 입니다."
        // Then
//        assertEquals(exception_code, -100);
    }

    @Test
    @DisplayName("UserService - 이메일 형식이 아닌 아이디 회원가입 테스트")
    void notEmailFormatIdJoinUser() {
        // Given
        UserSaveRequestDto userSaveRequestDto1 = new UserSaveRequestDto();
        userSaveRequestDto1.setEmail("ay9564naver.com");
        userSaveRequestDto1.setName("서영준");
        userSaveRequestDto1.setPassword("ay789456");

        // When
//        Long exception_code = userService.registerUser(userSaveRequestDto1);

        // exception_code : -200, message : "올바른 형식의 아이디가 아닙니다."
        // Then
//        assertEquals(exception_code, "올바른 형식의 아이디가 아닙니다.");
    }
}
