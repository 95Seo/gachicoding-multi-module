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

    private Long user_idx;
    private String social_id;
    private String name;
    private String type;

    @Builder
    public SocialSaveRequestDto(Long user_idx, String social_id, String name, String type) {
        this.user_idx = user_idx;
        this.social_id = social_id;
        this.name = name;
        this.type = type;
    }

    public Social toEntity(){
        return Social.builder()
                .user_idx(user_idx)
                .social_id(social_id)
                .type(type)
                .build();
    }
}
