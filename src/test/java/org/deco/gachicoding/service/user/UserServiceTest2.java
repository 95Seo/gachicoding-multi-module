package org.deco.gachicoding.service.user;

import org.deco.gachicoding.domain.user.User;
import org.deco.gachicoding.dto.user.UserSaveRequestDto;
import org.junit.jupiter.api.AfterEach;
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
public class UserServiceTest2 {

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

}
