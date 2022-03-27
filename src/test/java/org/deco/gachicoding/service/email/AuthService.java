package org.deco.gachicoding.service.email;

import lombok.RequiredArgsConstructor;
import org.deco.gachicoding.domain.utils.auth.Auth;
import org.deco.gachicoding.domain.utils.auth.AuthRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
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

    /**
     * 유효한 토큰 가져오기
     * @param confirmationTokenId
     * @return
     */

    public Auth getTokenByAuthEmail(String authEmail){
        Optional<Auth> auth = authRepository.findByAuthEmail(authEmail);
        return auth.orElseThrow(() -> new IllegalArgumentException("유효하지 않은 토큰입니다."));
    }

//
    public Auth findByIdExpirationDateAfterAndExpired(String confirmationTokenId) {
//        Optional<ConfirmationToken> confirmationToken = confirmationTokenRepository.findByIdAndExpirationDateAfterAndExpired(confirmationTokenId, LocalDateTime.now(), false);
//        return confirmationToken.orElseThrow(()-> new IllegalArgumentException("기한이 만료된 토큰입니다."));
        return null;
    }
}
