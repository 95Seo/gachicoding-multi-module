package org.deco.gachicoding.controller;

import org.deco.gachicoding.config.jwt.JwtTokenProvider;
import org.deco.gachicoding.service.user.impl.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// 컨트롤러 테스트에서 데이터의 유효성, API의 반환값에 대한 검증 테스트를 진행한다.
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = RestUserController.class)	// (2)
public class RestUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    UserServiceImpl userServiceImpl;

    @InjectMocks
    JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("UserService - 이메일 형식이 아닌 아이디 회원가입 테스트")
    void notEmailFormatIdJoinUser() throws Exception {
        // Given
        String message = "올바른 형식의 아이디가 아닙니다.";

        mockMvc.perform(post("/user")
                        .param("name", "서영준")
                        .param("email", "ay1516naver.com")
                        .param("password", "ay789456"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(message));
    }
}
