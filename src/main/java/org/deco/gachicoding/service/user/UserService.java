package org.deco.gachicoding.service.user;

import org.deco.gachicoding.domain.user.User;
import org.deco.gachicoding.dto.user.UserResponseDto;
import org.deco.gachicoding.dto.user.UserSaveRequestDto;
import org.deco.gachicoding.dto.user.UserUpdateResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User getUserByEmail(String email);

    UserResponseDto getUser(Long idx);

    Long registerUser(UserSaveRequestDto dto);

    void confirmEmail(String token);

    Long updateUser(Long idx, UserUpdateResponseDto dto);

    Long deleteUser(Long idx);


}
