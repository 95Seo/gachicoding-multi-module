package org.deco.gachicoding.domain.utils.email;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@Table(name = "email_token")
public class EmailToken {

    private static final long EMAIL_TOKEN_EXPIRATION_TIME_VALUE = 5L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(length = 36)    // 토큰의 PK 값
    private String token;

    @Column     // 만료 시간
    private LocalDateTime expirationDate;

    @Column     // 만료 여부
    private boolean expired;

    // 일부러 FK 사용 안함
    @Column     // USER 의 PK 값
    private String email;

    @CreatedDate
    @Column(updatable = false)  // 생성 시간
    private LocalDateTime regdate;

//    @LastModifiedDate           // 마지막 변경 시간
//    private LocalDateTime lastModifiedDate;

    /**
     * 이메일 인증 토큰 생성
     * @param email
     * @return
     */
    public static EmailToken createEmailConfirmationToken(String email) {
        EmailToken confirmationToken = new EmailToken();
        confirmationToken.expirationDate = LocalDateTime.now().plusMonths(EMAIL_TOKEN_EXPIRATION_TIME_VALUE); // 5분후 만료
        confirmationToken.email = email;
        confirmationToken.expired = false;
        return confirmationToken;
    }

    /**
     * 토큰 사용으로 인한 만료
     */
    public void useToken() {
        expired = true;
    }
}
