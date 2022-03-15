package org.deco.gachicoding.service;

import org.deco.gachicoding.domain.user.User;
import org.deco.gachicoding.dto.user.UserResponseDto;
import org.deco.gachicoding.dto.user.UserSaveRequestDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    public UserResponseDto getUser(Long idx);

    public Long registerUser(UserSaveRequestDto dto);

}
