package org.deco.gachicoding.domain.social;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@DynamicInsert
@Entity
@NoArgsConstructor
@Table(name = "social")
public class Social {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long socialIdx;

    @NotNull
    private Long userIdx;

    @NotNull
    private String socialId;

    @NotNull
    private String socialType;

    @CreatedDate @NotNull
    @Column(updatable = false)  // 생성 시간
    private LocalDateTime authDate;

    @Builder
    public Social(Long userIdx, String socialId, String socialType, LocalDateTime authDate) {
        this.userIdx = userIdx;
        this.socialId = socialId;
        this.socialType = socialType;
        this.authDate = authDate;
    }
}
