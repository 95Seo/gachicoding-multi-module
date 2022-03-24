package org.deco.gachicoding.service.user;

import org.deco.gachicoding.domain.user.User;
import org.deco.gachicoding.dto.user.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {

    Optional<User> getUserByEmail(String email);

    UserResponseDto getUser(Long idx);

    JwtResponseDto login(JwtRequestDto request);

    Long registerUser(UserSaveRequestDto dto);

    void confirmEmail(String token);

    Long updateUser(Long idx, UserUpdateResponseDto dto);

    Long deleteUser(Long idx);


}
