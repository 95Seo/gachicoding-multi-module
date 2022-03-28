package org.deco.gachicoding.service.user;


import org.deco.gachicoding.domain.user.User;
import org.deco.gachicoding.dto.jwt.JwtRequestDto;
import org.deco.gachicoding.dto.jwt.JwtResponseDto;
import org.deco.gachicoding.dto.user.UserSaveRequestDto;
import org.deco.gachicoding.dto.user.UserUpdateRequestDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {

    // 이메일 중복 체크
    boolean isDuplicateEmail(String email);

    Optional<User> getUserByEmail(String email);

    JwtResponseDto login(JwtRequestDto request);

    Long registerUser(UserSaveRequestDto dto);

    void confirmEmail(String token);

    Long updateUser(Long idx, UserUpdateRequestDto dto);

    Long deleteUser(Long idx);


}
