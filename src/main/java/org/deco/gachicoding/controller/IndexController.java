package org.deco.gachicoding.controller;

import lombok.RequiredArgsConstructor;
import org.deco.gachicoding.dto.user.UserSaveRequestDto;
import org.deco.gachicoding.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class IndexController {

    private final UserService userService;

    @GetMapping("/")
    public Long index() {
        UserSaveRequestDto dto = UserSaveRequestDto.builder()
                .email("inhan1009@naver.com")
                .name("김인환")
                .password("12341dfasdf")
                .build();

        return userService.save(dto);
    }

}