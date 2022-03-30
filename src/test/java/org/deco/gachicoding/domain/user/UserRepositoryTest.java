package org.deco.gachicoding.domain.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

//assertEquals(a, b);	객체 A와 B의 실제 값이 일치한지 확인한다.
//assertSame(a, b);     객체 A와 B가 같은 객체임을 확인한다.
//        - assertEquals 메서드는 두 객체의 값의 비교
//        - assertSame 메서드는 두 객체가 동일한지 객체의 비교 (== 연산자와 같다)

//assertTrue(a);	조건 A가 참인가를 확인한다.
//assertNotNull(a);	객체 A가 null이 아님을 확인한다.

// 리포지토리 테스트 에선 디비와 연동된 CRUD 기능을 테스트 한다(관련된 예외처리에 대해선 아직 잘 모르겠음)
// @SpringBootTest : 스프링 부트 환경에서 모든 의존성을 제공
@DataJpaTest // JPA 에 관련된 의존성 제공, 자동으로 롤백
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// AutoConfigureTestDatabase => ANY(기본설정) : 내장 메모리 DB를 사용(휘발성)
// NONE : DataSource를 이용한 외장 DB사용
// **주의 ANY설정 시 DataSource 정보가 application.properties에 있다면 에러 발생
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    private Long createUserMock(String userName, String userNick, String userEmail, String userPassword) {
        User entity = User.builder()
                .userName(userName)
                .userNick(userNick)
                .userEmail(userEmail)
                .userPassword(userPassword)
                .build();

        return userRepository.save(entity).getUserIdx();
    }

    @Test
    public void 인덱스로_유저조회() {

        Long userIdx = createUserMock("테스트", "테스트 별명", "test@test.com", "1234");

        Optional<User> user = userRepository.findById(userIdx);
        assertEquals("test@test.com", user.get().getUserEmail());
        assertEquals("테스트", user.get().getUserName());
    }

    @Test
    public void 이메일로_유저조회() {

        createUserMock("테스트", "테스트 별명", "test@test.com", "1234");

        Optional<User> user = userRepository.findByUserEmail("test@test.com");

        assertEquals("test@test.com", user.get().getUserEmail());
        assertEquals("테스트", user.get().getUserName());
    }

    @Test
    public void 닉네임으로_유저조회() {
        Long userIdx = createUserMock("테스트", "테스트 별명", "test@test.com", "1234");

        Optional<User> findUser = userRepository.findByUserNick("테스트 별명");
        Optional<User> createUser = userRepository.findById(userIdx);
        assertEquals(createUser, findUser);
    }

    @Test
    public void 유저_저장() {

        String userName = "가치코딩";
        String userNick = "가치코코코딩";
        String userEmail = "gachicoding@gachicoding.com";
        String userPassword = "gachi1234";

        User entity = User.builder()
                .userName(userName)
                .userNick(userNick)
                .userEmail(userEmail)
                .userPassword(userPassword)
                .build();


        Long userIdx = userRepository.save(entity).getUserIdx();
        User user = userRepository.findById(userIdx).get();

        assertEquals(entity, user);

    }

    @Test
    public void 인덱스로_유저_삭제() {

        Long userIdx = createUserMock("테스트", "테스트 별명", "test@test.com", "1234");

        Optional<User> user = userRepository.findById(userIdx);

        assertTrue(user.isPresent());

        userRepository.deleteById(userIdx);
        user = userRepository.findById(userIdx);

        assertTrue(user.isEmpty());
    }

    @Test
    public void 유저정보_수정() {

        String userName = "가치코딩";
        String userNick = "가치코코코딩";
        String userEmail = "gachicoding@gachicoding.com";
        String userPassword = "gachi1234";

        User entity = User.builder()
                .userName(userName)
                .userNick(userNick)
                .userEmail(userEmail)
                .userPassword(userPassword)
                .build();

        Long userIdx = userRepository.save(entity).getUserIdx();
        User user = userRepository.findById(userIdx).get();

        String updateName = "수정된 이름";
        String updateNick = "수정된 별명";
        String updatePassword = "수정된 비밀번호";
        boolean updateAuth = true;
        int updateAct = 0;
        String updatePicture = "수정된 사진";
        UserRole updateRole = UserRole.GUEST;

        user.update(updateNick, updatePassword, updateAct, updateAuth, updatePicture, updateRole);

        assertEquals(updateName, userRepository.findById(userIdx).get().getUserName());
        assertEquals(updateNick, userRepository.findById(userIdx).get().getUserNick());
        assertEquals(updatePassword, userRepository.findById(userIdx).get().getUserPassword());
        assertEquals(updateAuth, userRepository.findById(userIdx).get().isUserAuth());
        assertEquals(updateAct, userRepository.findById(userIdx).get().getUserActivated());
        assertEquals(updatePicture, userRepository.findById(userIdx).get().getUserPicture());
        assertEquals(updateRole, userRepository.findById(userIdx).get().getUserRole());
    }
}
