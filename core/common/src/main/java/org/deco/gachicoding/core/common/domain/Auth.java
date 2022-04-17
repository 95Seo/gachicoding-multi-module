package org.deco.gachicoding.core.common.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@Table(name = "auth")
public class Auth {

    private static final long EMAIL_TOKEN_EXPIRATION_TIME_VALUE = 5L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(length = 36)
    private String authToken;

    // 일부러 FK 사용 안함
    private String authEmail;

    @CreatedDate
    private LocalDateTime authRegdate;
    private LocalDateTime authExpdate;

    /**
     * 이메일 인증 토큰 생성
     * @param email
     * @return
     */
    public static Auth createEmailConfirmationToken(String email) {
        Auth authenticationToken = new Auth();
        authenticationToken.authExpdate = LocalDateTime.now().plusMinutes(EMAIL_TOKEN_EXPIRATION_TIME_VALUE); // 5분후 만료
        authenticationToken.authEmail = email;
//        authenticationToken.expired = false;
        return authenticationToken;
    }

    /**
     * 토큰 사용으로 인한 만료
     */
    public void useToken() {
//        expired = true;
    }
}
