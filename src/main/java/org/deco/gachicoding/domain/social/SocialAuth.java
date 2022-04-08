package org.deco.gachicoding.domain.social;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@DynamicInsert
@Entity
@NoArgsConstructor
@Table(name = "social_auth")
public class SocialAuth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long socialIdx;

    @Column
    @Nullable
    private Long userIdx;

    @Column
    @Nullable
    private String socialId;

    @Column
    @Nullable
    private String socialType;

    @CreatedDate
    @Column(updatable = false)  // 생성 시간
    private LocalDateTime authDate;

    @Builder
    public SocialAuth(Long socialIdx, Long userIdx, String socialId, String socialType, LocalDateTime authDate) {
        this.socialIdx = socialIdx;
        this.userIdx = userIdx;
        this.socialId = socialId;
        this.socialType = socialType;
        this.authDate = authDate;
    }
}
