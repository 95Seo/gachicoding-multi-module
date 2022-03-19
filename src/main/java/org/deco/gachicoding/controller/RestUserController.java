package org.deco.gachicoding.controller;

import lombok.RequiredArgsConstructor;
import org.deco.gachicoding.dto.user.*;
import org.deco.gachicoding.service.user.UserService;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class RestUserController {

    private final UserService userService;

    @PostMapping("/user/login")
    public JwtResponseDto login(@RequestBody JwtRequestDto dto) throws Exception {
        return userService.login(dto);
    }

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

    @DeleteMapping("/user/{idx}")
    public Long deleteUser(@PathVariable Long idx){
        return userService.deleteUser(idx);
    }


}