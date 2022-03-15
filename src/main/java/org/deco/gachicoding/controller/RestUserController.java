package org.deco.gachicoding.controller;

import lombok.RequiredArgsConstructor;
import org.deco.gachicoding.dto.user.UserResponseDto;
import org.deco.gachicoding.dto.user.UserSaveRequestDto;
import org.deco.gachicoding.dto.user.UserUpdateResponseDto;
import org.deco.gachicoding.service.UserService;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class RestUserController {

    private final UserService userService;

    @GetMapping("/user/{idx}")
    public UserResponseDto getUser(@PathVariable Long idx){
        return userService.getUser(idx);
    }

    @PostMapping("/user")
    public Long registerUser(@RequestBody UserSaveRequestDto dto) {
        return userService.registerUser(dto);
    }

    @PutMapping("/user/{idx}")
    public Long updateUser(@PathVariable Long idx,@RequestBody UserUpdateResponseDto dto){
        return userService.updateUser(idx, dto);
    }
}