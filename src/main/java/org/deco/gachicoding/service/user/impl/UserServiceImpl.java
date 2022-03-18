package org.deco.gachicoding.service.user.impl;

import lombok.RequiredArgsConstructor;
import org.deco.gachicoding.domain.user.User;
import org.deco.gachicoding.domain.user.UserRepository;
import org.deco.gachicoding.domain.utils.email.ConfirmationToken;
import org.deco.gachicoding.dto.user.JwtRequestDto;
import org.deco.gachicoding.dto.user.UserResponseDto;
import org.deco.gachicoding.dto.user.UserSaveRequestDto;
import org.deco.gachicoding.dto.user.UserUpdateResponseDto;
import org.deco.gachicoding.service.user.UserService;
import org.deco.gachicoding.service.email.ConfirmationTokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ConfirmationTokenService confirmationTokenService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Transactional
    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(()-> new IllegalArgumentException("회원이 존재하지 않습니다. 이메일 = " + email));
    }

    @Transactional
    @Override
    public UserResponseDto getUser(Long idx) {

        User user = userRepository.findById(idx)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다. 회원 번호 = " + idx));

        return new UserResponseDto(user);
    }

    @Transactional
    @Override
    public String login(JwtRequestDto request) throws Exception {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();

        return principal.getUsername();
    }

    @Transactional
    @Override
    public Long registerUser(UserSaveRequestDto dto) {
        System.out.println("User Save 수행");

        dto.encryptPassword(passwordEncoder);

        Long idx = userRepository.save(dto.toEntity()).getIdx();

        // 이메일 인증 기능 분리 필요
        confirmationTokenService.createEmailConfirmationToken(dto.getEmail());
        return idx;
    }

    /**
     * 이메일 인증 로직
     * @param token
     */
    @Transactional
    @Override
    public void confirmEmail(String token) {
        ConfirmationToken findConfirmationToken = confirmationTokenService.findByIdExpirationDateAfterAndExpired(token);
        User findUserInfo = getUserByEmail(findConfirmationToken.getEmail());
        findConfirmationToken.useToken();   // 토큰 만료 로직을 구현해주면 된다. ex) expired 값을 true 로 변경
//        findUserInfo.emailVerifiedSuccess();    // 유저의 이메일 인증 값 변경 로직을 구현해 주면 된다. ex) emailVerified 값을 true로 변경
    }

    @Transactional
    @Override
    public Long updateUser(Long idx, UserUpdateResponseDto dto) {

        User user = userRepository.findById(idx)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다. 회원 번호 = " + idx));

        user.update(dto.getName(), dto.getEmail(), dto.getPassword(), dto.getActivated(), dto.getRole());

        return idx;
    }

    @Transactional
    @Override
    public Long deleteUser(Long idx) {
        userRepository.deleteById(idx);
        return idx;
    }
}