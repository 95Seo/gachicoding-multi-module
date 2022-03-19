package org.deco.gachicoding.domain.user;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Mock
    User user;

    @Test
    public void getName() {
        user = User.builder()
                .name("서영준")
                .email("ay9564@naver.com")
                .password("ay789456")
                .build();

        final String name = user.getName();
        assertEquals("서영준", name);
    }

}