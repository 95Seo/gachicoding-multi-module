package org.deco.gachicoding.domain.user;

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
    private Long idx;

    @Column
    @Nullable
    private Long user_idx;

    @Column
    @Nullable
    private String socialId;

    @Column
    @Nullable
    private String type;

    @CreatedDate
    @Column(updatable = false)  // 생성 시간
    private LocalDateTime auth_date;

    @Builder
    public SocialAuth(Long idx, Long user_idx, String social_id, String type, LocalDateTime auth_date) {
        this.idx = idx;
        this.user_idx = user_idx;
        this.socialId = social_id;
        this.type = type;
        this.auth_date = auth_date;
    }
}
