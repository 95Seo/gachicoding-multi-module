package org.deco.gachicoding.service.impl;

import lombok.RequiredArgsConstructor;
import org.deco.gachicoding.domain.user.User;
import org.deco.gachicoding.domain.user.UserRepository;
import org.deco.gachicoding.dto.user.UserResponseDto;
import org.deco.gachicoding.dto.user.UserSaveRequestDto;
import org.deco.gachicoding.service.UserService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponseDto getUser(Long idx) {

        User entity = userRepository.findById(idx)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다. 회원 번호 = " + idx));

        return new UserResponseDto(entity);
    }

    @Transactional
    @Override
    public Long registerUser(UserSaveRequestDto dto) {

        System.out.println("User Save 수행");
        return userRepository.save(dto.toEntity()).getIdx();
    }


}
