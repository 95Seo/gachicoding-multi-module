package org.deco.gachicoding.service.user;

import org.deco.gachicoding.domain.user.User;
import org.deco.gachicoding.domain.user.UserRepository;
import org.deco.gachicoding.dto.user.UserSaveRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

// 서비스 테스트에서 비즈니스 로직에서 발생할 수 있는 예외 상황의 테스트를 진행한다
//@ExtendWith(SpringExtension.class)

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserServiceTest2 {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository; // DB 초기화 위한

    @AfterEach
    void cleanUp() {
        userRepository.deleteAll();
    }

    @Test
    void 이메일로_유저정보_가져오기_해당유저존재() {
        createUserMock("테스트", "테스트별명", "test@test.com", "1234");
        String userEmail = "test@test.com";
        Optional<User> user = userService.getUserByEmail(userEmail);
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

        createUserMock("테스트", "테스트별명", "test@test.com", "1234");
        assertTrue(userService.isDuplicateEmail("test@test.com"));
    }

    @Test
    void 중복이메일_존재하지_않음() {

        String email = "inhan1009@naver.com";
        assertFalse(userService.isDuplicateEmail(email));
    }

    @Test
    void 회원가입_성공() {

        String userEmail = "test@test.com";
        String userName = "테스트";
        String userNick = "테스트별명";
        String userPassword = "1234";

        createUserMock(userName, userNick, userEmail, userPassword);

        Optional<User> user = userService.getUserByEmail(userEmail);

        assertEquals(userName, user.get().getUserName());
        assertEquals(userNick, user.get().getUserNick());
        assertEquals(userEmail, user.get().getUserEmail());
        assertEquals(userPassword, user.get().getUserPassword());
    }

    @Test
    void 회원가입_실패_이메일_중복인_경우() {

        // 첫 이메일
        createUserMock("테스트", "테스트별명", "test@test.com", "1234");

        assertThrows(DataIntegrityViolationException.class, () -> {
            // 중복된 이메일로 회원가입 시도
            createUserMock("테스트", "테스트별명", "test@test.com", "1234");
        });

    }

    private Long createUserMock(String userName, String userNick, String userEmail, String userPassword) {

        UserSaveRequestDto dto = UserSaveRequestDto.builder()
                .userName(userName)
                .userNick(userNick)
                .userEmail(userEmail)
                .userPassword(userPassword)
                .build();

        return userService.registerUser(dto);
    }

}
