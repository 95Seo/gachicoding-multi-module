package org.deco.gachicoding.service;

import org.deco.gachicoding.domain.user.User;
import org.deco.gachicoding.dto.user.UserResponseDto;
import org.deco.gachicoding.dto.user.UserSaveRequestDto;
import org.deco.gachicoding.dto.user.UserUpdateResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    UserResponseDto getUser(Long idx);

    Long registerUser(UserSaveRequestDto dto);

    Long updateUser(Long idx, UserUpdateResponseDto dto);
}
