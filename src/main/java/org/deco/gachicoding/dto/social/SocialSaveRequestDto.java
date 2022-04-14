package org.deco.gachicoding.dto.social;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.deco.gachicoding.domain.social.Social;

@Getter
@Setter
@NoArgsConstructor
public class SocialSaveRequestDto {

    private Long userIdx;
    private String socialId;
    private String userName;
    private String socialType;

    @Builder
    public SocialSaveRequestDto(Long userIdx, String socialId, String userName, String socialType) {
        this.userIdx = userIdx;
        this.socialId = socialId;
        this.userName = userName;
        this.socialType = socialType;
    }

    public Social toEntity(){
        return Social.builder()
                .userIdx(userIdx)
                .socialId(socialId)
                .socialType(socialType)
                .build();
    }
}
