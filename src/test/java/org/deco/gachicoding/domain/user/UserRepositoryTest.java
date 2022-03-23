package org.deco.gachicoding.domain.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    public void 이메일로_유저조회() {

        Optional<User> user = userRepository.findByEmail("inhan1009@naver.com");

        assertEquals("inhan1009@naver.com", user.get().getEmail());
        assertEquals("김인환", user.get().getName());
    }

    @Test
    public void 인덱스로_유저조회() {
        Long idx = (long) 61;
        Optional<User> user = userRepository.findById(idx);
        assertEquals("inhan1009@naver.com", user.get().getEmail());
        assertEquals("김인환", user.get().getName());
    }

    @Test
    public void 유저_저장() {

        String name = "가치코딩";
        String email = "gachicoding@gachicoding.com";
        String password = "gachi1234";
        LocalDateTime regdate = LocalDateTime.now();
        int activated = 1;
        Role role = Role.USER;

        User entity = User.builder()
                .name(name)
                .email(email)
                .password(password)
                .regdate(regdate)
                .activated(activated)
                .role(role)
                .build();


        userRepository.save(entity);
        List<User> userList = userRepository.findAll();
        User user = userList.get(userList.size() - 1);

        assertEquals(name, user.getName());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertEquals(regdate, user.getRegdate());
        assertEquals(activated, user.getActivated());
        assertEquals(role, user.getRole());
    }
}
