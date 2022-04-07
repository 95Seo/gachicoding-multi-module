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
    UserRepository userRepository;

    private Long userIdx;

    private Long createUserMock(String userName, String userNick, String userEmail, String userPassword) {

        UserSaveRequestDto dto = UserSaveRequestDto.builder()
                .userName(userName)
                .userNick(userNick)
                .userEmail(userEmail)
                .userPassword(userPassword)
                .build();

        return userService.registerUser(dto);
    }

    @AfterEach
    void cleanUp() {
        if (userIdx != null) {
            userRepository.deleteById(this.userIdx);
        }
        userIdx = null;
    }

    @Test
    void 이메일로_유저정보_가져오기_해당유저존재() {

        userIdx = createUserMock("테스트", "테스트별명", "test@test.com", "1234");

        Optional<User> user = userService.getUserByUserEmail("test@test.com");
        assertTrue(user.isPresent());
    }

    @Test
    void 이메일로_유저정보_가져오기_해당유저없음() {

        Optional<User> user = userService.getUserByUserEmail("inhan1009@naver.com");
        assertTrue(user.isEmpty());
    }

    @Test
    void 중복이메일_존재() {

        userIdx = createUserMock("테스트", "테스트별명", "test@test.com", "1234");
        assertTrue(userService.isDuplicateEmail("test@test.com"));
    }

    @Test
    void 중복이메일_존재하지_않음() {

        assertFalse(userService.isDuplicateEmail("inhan1009@naver.com"));
    }

    @Test
    void 회원가입_성공() {

        userIdx = createUserMock("테스트", "테스트별명", "test@test.com", "1234");

        Optional<User> user = userService.getUserByUserEmail("test@test.com");

        assertEquals("테스트", user.get().getUserName());
        assertEquals("테스트별명", user.get().getUserNick());
        assertEquals("test@test.com", user.get().getUserEmail());
    }

    @Test
    void 회원가입_실패_이메일_중복인_경우() {

        // 회원가입
        userIdx = createUserMock("테스트", "테스트별명", "test@test.com", "1234");

        // 중복된 이메일로 회원가입 할 경우 예외발생하는지 체크
        assertThrows(DataIntegrityViolationException.class, () -> {
            // 이미 등록된 이메일로 한번 더 회원가입
            createUserMock("테스트", "테스트별명", "test@test.com", "1234");
        });

    }

    @Test
    void 로그인_성공(){

    }

}