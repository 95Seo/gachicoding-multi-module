package org.deco.gachicoding.api.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.deco.gachicoding.api.user.service.EmailSenderService;
import org.deco.gachicoding.core.common.domain.Auth;
import org.deco.gachicoding.core.common.repository.AuthRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    private final EmailSenderService emailSenderService;

    /**
     * 이메일 인증 토큰 생성
     * @return
     */
    public String createEmailConfirmationToken(String receiverEmail) {

        Assert.hasText(receiverEmail, "receiverEmail은 필수 입니다.");

        Auth auth = Auth.createEmailConfirmationToken(receiverEmail);
        authRepository.save(auth);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(receiverEmail);
        mailMessage.setSubject("회원가입 이메일 인증");
        mailMessage.setText("http://localhost:8090/confirm-email?token="+auth.getAuthToken());
        emailSenderService.sendEmail(mailMessage);

        return auth.getAuthToken();

    }

    public Auth getTokenByAuthEmail(String authEmail){
        Optional<Auth> auth = authRepository.findByAuthEmail(authEmail);
        return auth.orElseThrow(() -> new IllegalArgumentException("유효하지 않은 토큰입니다."));
    }

    public Auth findByIdExpirationDateAfterAndExpired(String confirmationTokenId) {
//        Optional<ConfirmationToken> confirmationToken = confirmationTokenRepository.findByIdAndExpirationDateAfterAndExpired(confirmationTokenId, LocalDateTime.now(), false);
//        return confirmationToken.orElseThrow(()-> new IllegalArgumentException("기한이 만료된 토큰입니다."));
        return null;
    }
}
