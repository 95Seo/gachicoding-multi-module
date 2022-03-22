package org.deco.gachicoding.domain.user;

import org.hamcrest.collection.IsEmptyCollection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

//assertEquals(a, b);	객체 A와 B의 실제 값이 일치한지 확인한다.
//assertSame(a, b);     객체 A와 B가 같은 객체임을 확인한다.
//        - assertEquals 메서드는 두 객체의 값의 비교
//        - assertSame 메서드는 두 객체가 동일한지 객체의 비교 (== 연산자와 같다)

//assertTrue(a);	조건 A가 참인가를 확인한다.
//assertNotNull(a);	객체 A가 null이 아님을 확인한다.

// 리포지토리 테스트 에선 디비와 연동된 CRUD 기능을 테스트 한다(관련된 예외처리에 대해선 아직 잘 모르겠음)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// AutoConfigureTestDatabase => ANY(기본설정) : 내장 메모리 DB를 사용(휘발성) 
// NONE : DataSource를 이용한 외장 DB사용
// **주의 ANY설정 시 DataSource 정보가 application.properties에 있다면 에러 발생
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    UserRepository userRepository;

    @Test
    @Rollback(false)
    @DisplayName("UserRepository - 회원가입 테스트")
    public void UserJoinTest() {
        //given - 준비
        User user = User.builder()
                .name("서영준")
                .email("ay9564@naver.com")
                .password("ay789456")
                .build();

        //when - 실행
        testEntityManager.persist(user);

        //then - 검증
        assertEquals(user, testEntityManager.find(User.class, user.getIdx()));

    }

    @Test
    @DisplayName("UserRepository - 회원가입 후 유저 리스트 불러오기 테스트")
    public void UserJoinAndFindTest() {
        //given
        User user1 = User.builder()
                .name("서영준")
                .email("ay9564@naver.com")
                .password("ay789456")
                .build();

        testEntityManager.persist(user1);

        User user2 = User.builder()
                .name("김인환")
                .email("inhwan@naver.com")
                .password("log4junit")
                .build();

        testEntityManager.persist(user2);

        User user3 = User.builder()
                .name("김인표")
                .email("inpyo@naver.com")
                .password("javascriptreact")
                .build();

        testEntityManager.persist(user3);

        //when
        List<User> userList = userRepository.findAll();

        //then
        assertEquals(userList.size(), 3);
        assertEquals(user2, userList.get(1));

        int i = 0;
        for(User u : userList) {
            System.out.println("userList[" + i++ + "] : " + u.getName());
        }
    }

    @Test
    @DisplayName("UserRepository - 회원가입 후 이메일로 찾기(로그인) 테스트")
    public void UserJoinAndEmailFindTest() {
        //given
        User user1 = User.builder()
                .name("서영준")
                .email("ay7871@naver.com")
                .password("ay789456")
                .build();

        testEntityManager.persist(user1);

        User user2 = User.builder()
                .name("김인환")
                .email("inhwan@naver.com")
                .password("log4junit")
                .build();

        testEntityManager.persist(user2);

        User user3 = User.builder()
                .name("김인표")
                .email("inpyo@naver.com")
                .password("javascriptreact")
                .build();

        testEntityManager.persist(user3);

        //when
        User user = userRepository.findByEmail("ay7871@naver.com")
                .orElseThrow(()-> new IllegalArgumentException("회원이 존재하지 않습니다."));

        //then
        assertEquals("서영준", user.getName());
//        assertEquals(user2, userList.get(1));

        System.out.println("userName " + user.getName());
    }

    @Test
    @DisplayName("UserRepository - 회원가입 후 삭제 테스트")
    public void UserJoinAndDeleteTest() {
        //given
        User user1 = User.builder()
                .name("서영준")
                .email("ay9564@naver.com")
                .password("ay789456")
                .build();

        testEntityManager.persist(user1);

        User user2 = User.builder()
                .name("김인환")
                .email("inhwan@naver.com")
                .password("log4junit")
                .build();

        testEntityManager.persist(user2);

        List<User> userList = userRepository.findAll();

        assertEquals(userList.size(), 2);
        assertEquals(user1, userList.get(0));

        //when
        userRepository.deleteAll();

        //then
        assertThat(userRepository.findAll(), IsEmptyCollection.empty());
    }
}
