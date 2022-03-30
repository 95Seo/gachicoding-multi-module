package org.deco.gachicoding.service.user.impl;

import lombok.RequiredArgsConstructor;
import org.deco.gachicoding.config.jwt.JwtTokenProvider;
import org.deco.gachicoding.domain.user.*;
import org.deco.gachicoding.domain.utils.email.EmailToken;
import org.deco.gachicoding.dto.jwt.JwtRequestDto;
import org.deco.gachicoding.dto.jwt.JwtResponseDto;
import org.deco.gachicoding.dto.user.*;
import org.deco.gachicoding.service.user.UserService;
import org.deco.gachicoding.service.email.EmailTokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EmailTokenService confirmationTokenService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
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
    public JwtResponseDto login(JwtRequestDto request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            return createJwtToken(authentication);
            // BadCredentialsException - 스프링 시큐리티 에서 아이디 또는 비밀번호가 틀렸을 경우 나오는 예외
        } catch (BadCredentialsException e) {
            e.printStackTrace();
            return new JwtResponseDto("아이디 또는 비밀번호를 확인해 주세요.");
        }
    }

    private JwtResponseDto createJwtToken(Authentication authentication) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        String token = jwtTokenProvider.generateToken(principal);
        return new JwtResponseDto(token);
    }

    @Override
    public Long registerUser(UserSaveRequestDto dto) {

        dto.encryptPassword(passwordEncoder);

        if(getUserByEmail(dto.getEmail()).isEmpty()) {
            System.out.println("User Save 수행");

            Long idx = userRepository.save(dto.toEntity()).getIdx();

            // 이메일 인증 기능 분리 필요
            confirmationTokenService.createEmailConfirmationToken(dto.getEmail());

            return idx;
        } else {
            System.out.println(dto.getEmail() + " : User Save 실패\n 중복된 아이디 입니다.");
            return Long.valueOf(-100);
        }
    }

    /**
     * 이메일 인증 로직
     * @param token
     */
    @Transactional
    @Override
    public void confirmEmail(String token) {
        EmailToken findConfirmationToken = confirmationTokenService.findByIdExpirationDateAfterAndExpired(token);
        Optional<User> findUserInfo = getUserByEmail(findConfirmationToken.getEmail());
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