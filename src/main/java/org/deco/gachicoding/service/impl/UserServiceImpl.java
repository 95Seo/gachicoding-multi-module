package org.deco.gachicoding.service.impl;

import lombok.RequiredArgsConstructor;
import org.deco.gachicoding.domain.user.User;
import org.deco.gachicoding.domain.user.UserRepository;
import org.deco.gachicoding.dto.user.UserResponseDto;
import org.deco.gachicoding.dto.user.UserSaveRequestDto;
import org.deco.gachicoding.dto.user.UserUpdateResponseDto;
import org.deco.gachicoding.service.UserService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public UserResponseDto getUser(Long idx) {

        User user = userRepository.findById(idx)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다. 회원 번호 = " + idx));

        return new UserResponseDto(user);
    }

    @Transactional
    @Override
    public Long registerUser(UserSaveRequestDto dto) {

        System.out.println("User Save 수행");
        return userRepository.save(dto.toEntity()).getIdx();
    }

    @Transactional
    @Override
    public Long updateUser(Long idx, UserUpdateResponseDto dto) {

        User user = userRepository.findById(idx)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다. 회원 번호 = " + idx));

        user.update(dto.getName(), dto.getEmail(), dto.getPassword(), dto.getActivated(), dto.getRole());

        return idx;
    }


}
