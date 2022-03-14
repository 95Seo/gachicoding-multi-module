package org.deco.gachicoding.controller;

import lombok.RequiredArgsConstructor;
import org.deco.gachicoding.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {

    final UserService userService;

    @GetMapping("confirm-email")
    public String viewConfirmEmail(@Valid @RequestParam String token) {
        userService.confirmEmail(token);

        return "redirect:/login";
    }
}
