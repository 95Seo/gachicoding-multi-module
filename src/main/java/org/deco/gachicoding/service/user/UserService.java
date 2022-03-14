package org.deco.gachicoding.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.deco.gachicoding.domain.user.User;
import org.deco.gachicoding.domain.user.UserRepository;
import org.deco.gachicoding.domain.utils.email.ConfirmationToken;
import org.deco.gachicoding.service.email.ConfirmationTokenService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ConfirmationTokenService confirmationTokenService;

    public User findByUserEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * 이메일 인증 로직
     * @param token
     */
    public void confirmEmail(String token) {
        ConfirmationToken findConfirmationToken = confirmationTokenService.findByIdExpirationDateAfterAndExpired(token);
        User findUserInfo = findByUserEmail(findConfirmationToken.getEmail());
        findConfirmationToken.useToken();   // 토큰 만료 로직을 구현해주면 된다. ex) expired 값을 true로 변경
//        findUserInfo.emailVerifiedSuccess();    // 유저의 이메일 인증 값 변경 로직을 구현해 주면 된다. ex) emailVerified 값을 true로 변경
    }
}
