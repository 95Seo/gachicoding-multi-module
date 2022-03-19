package org.deco.gachicoding.service.user;

import org.deco.gachicoding.domain.user.User;
import org.deco.gachicoding.dto.user.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User getUserByEmail(String email);

    UserResponseDto getUser(Long idx);

    JwtResponseDto login(JwtRequestDto request) throws Exception;

    Long registerUser(UserSaveRequestDto dto);

    void confirmEmail(String token);

    Long updateUser(Long idx, UserUpdateResponseDto dto);

    Long deleteUser(Long idx);


}
