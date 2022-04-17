package org.deco.gachicoding.api.user.service;

import org.deco.gachicoding.api.user.dto.*;
import org.deco.gachicoding.core.common.domain.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {

    boolean isDuplicateEmail(String email);

    Optional<User> getUserByUserEmail(String email);

    JwtResponseDto login(JwtRequestDto request);

    Long registerUser(UserSaveRequestDto dto);

    void confirmEmail(String token);

    UserResponseDto updateUser(Long idx, UserUpdateRequestDto dto);

    void enableUser(Long idx);

    void disableUser(Long idx);

    void deleteUser(Long idx);

}
