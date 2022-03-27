package org.deco.gachicoding.service.user.impl;

import lombok.RequiredArgsConstructor;
import org.deco.gachicoding.config.jwt.JwtTokenProvider;
import org.deco.gachicoding.domain.user.User;
import org.deco.gachicoding.domain.user.UserRepository;
import org.deco.gachicoding.domain.utils.auth.Auth;
import org.deco.gachicoding.dto.jwt.JwtRequestDto;
import org.deco.gachicoding.dto.jwt.JwtResponseDto;
import org.deco.gachicoding.dto.user.UserSaveRequestDto;
import org.deco.gachicoding.dto.user.UserUpdateRequestDto;
import org.deco.gachicoding.service.email.AuthService;
import org.deco.gachicoding.service.user.UserService;
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
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    //    @Transactional
    @Override
    public boolean existDuplicateEmail(String email) {

        boolean isDuplicate = getUserByEmail(email).isPresent();

        return isDuplicate;
    }

    @Transactional
    @Override
    public Optional<User> getUserByEmail(String userEmail) {
        return userRepository.findByUserEmail(userEmail);
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

    // @Transactional - 아이디 중복 시 - Transaction silently rolled back because it has been marked as rollback-only 발생
    // 이유 트랜잭션은 재사용 될수 없다.
    // save하면서 같은 이메일이 있으면 예외를 발생, 예외 발생 시 기본 값으로 들어있는 롤백이 true가 됨
    // save가 끝나고 나오면서 registerUser로 돌아 왔을때 @Transactional어노테이션이 있으면
    // 커밋을 앞에서 예외를 잡았기 때문에 문제 없다고 판단, 커밋을 실행한다. 하지만 roll-back only**이 마킹되어 있어 **롤백함.
    // 에러 발생 - 이와 관련해선 좀 딥한 부분인거 같아서 공부를 좀 더 해야할 거 같음 + 트러블 슈팅으로 넣으면 좋을 듯
    // @Transactional 사용도 신중해야 할 필요가 있을 듯


    @Override
    public Long registerUser(UserSaveRequestDto dto) {

        /*
        dto.encryptPassword(passwordEncoder);

        if (getUserByEmail(dto.getEmail()).get() == null) {
            System.out.println("User Save 수행");

            Long idx = userRepository.save(dto.toEntity()).getIdx();

            // 이메일 인증 기능 분리 필요
            confirmationTokenService.createEmailConfirmationToken(dto.getEmail());

            return idx;
        } else {
            System.out.println(dto.getEmail() + " : User Save 실패\n 중복된 아이디 입니다.");
            return Long.valueOf(-100);
        }
        */


        // 이메일 중복 체크
        // 비밀번호 변조
        // 유저 저장
        // 유저 이메일로 인증 메일 보내기

        Long idx = userRepository.save(dto.toEntity()).getUserIdx();

        return idx;
    }


    // 클릭
    // 토큰 검색
    // 만료 일시 조회 와 지금 시각 비교

    // 만료 일시 지났는냐 아니냐

    // 지났으면 : 아무것도 안함 메시지 출력 다시 인증하세요
    // 지우는게 낫지않을까? 트리거 또는 프로시저 활용?

    // 안지났으면 : 인증 여부, 권한 업데이트
    // 지우는게 낫지않을까? 트리거 또는 프로시저 활용?

    /**
     * 이메일 인증 로직
     *
     * @param authEmail
     */

    @Transactional
    @Override
    public void confirmEmail(String authEmail) {
        Auth auth = authService.getTokenByAuthEmail(authEmail);
        Optional<User> findUserInfo = getUserByEmail(auth.getAuthEmail());
//        auth.useToken();   // 토큰 만료 로직을 구현해주면 된다. ex) expired 값을 true 로 변경
//        findUserInfo.emailVerifiedSuccess();    // 유저의 이메일 인증 값 변경 로직을 구현해 주면 된다. ex) emailVerified 값을 true로 변경
    }

    @Transactional
    @Override
    public Long updateUser(Long idx, UserUpdateRequestDto dto) {

        User user = userRepository.findById(idx)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다. 회원 번호 = " + idx));

//        user.update();

        return idx;
    }

    @Transactional
    @Override
    public Long deleteUser(Long idx) {
        userRepository.deleteById(idx);
        return idx;
    }
}