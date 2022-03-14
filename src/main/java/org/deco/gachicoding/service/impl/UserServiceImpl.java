package org.deco.gachicoding.service.impl;

import lombok.RequiredArgsConstructor;
import org.deco.gachicoding.domain.user.UserRepository;
import org.deco.gachicoding.dto.user.UserSaveRequestDto;
import org.deco.gachicoding.service.UserService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public Long save(UserSaveRequestDto dto) {

        System.out.println("User Save 수행");
        return userRepository.save(dto.toEntity()).getIdx();
    }

}
