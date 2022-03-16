package org.deco.gachicoding.controller;

import lombok.RequiredArgsConstructor;
import org.deco.gachicoding.dto.user.UserSaveRequestDto;
import org.deco.gachicoding.service.user.UserService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/user")
    public Long registerUser(@RequestBody UserSaveRequestDto dto) {

        return userService.registerUser(dto);
    }

    @GetMapping("confirm-email")
    public String viewConfirmEmail(@Valid @RequestParam String token) {
        userService.confirmEmail(token);

        return "redirect:/login";
    }
}
