package org.deco.gachicoding.service.email;

import lombok.RequiredArgsConstructor;
import org.deco.gachicoding.domain.utils.email.EmailToken;
import org.deco.gachicoding.domain.utils.email.EmailTokenRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailTokenService {

    private final EmailTokenRepository emailTokenRepository;
    private final EmailSenderService emailSenderService;

    /**
     * 이메일 인증 토큰 생성
     * @return
     */
    public String createEmailConfirmationToken(String receiverEmail) {

        Assert.hasText(receiverEmail, "receiverEmail은 필수 입니다.");

        EmailToken emailConfirmationToken = EmailToken.createEmailConfirmationToken(receiverEmail);
        emailTokenRepository.save(emailConfirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(receiverEmail);
        mailMessage.setSubject("회원가입 이메일 인증");
        mailMessage.setText("http://localhost:8090/confirm-email?token="+emailConfirmationToken.getToken());
        emailSenderService.sendEmail(mailMessage);

        return emailConfirmationToken.getToken();

    }

    /**
     * 유효한 토큰 가져오기
     * @param confirmationTokenId
     * @return
     */
    public EmailToken findByIdExpirationDateAfterAndExpired(String confirmationTokenId) {
        Optional<EmailToken> confirmationToken = emailTokenRepository.findByTokenAndExpirationDateAfterAndExpired(confirmationTokenId, LocalDateTime.now(), false);
        return confirmationToken.orElseThrow(()-> new IllegalArgumentException("기한이 만료된 토큰입니다."));
    }
}
