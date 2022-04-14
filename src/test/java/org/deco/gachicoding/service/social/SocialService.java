package org.deco.gachicoding.service.social;

import org.deco.gachicoding.domain.social.Social;
import org.deco.gachicoding.dto.social.SocialSaveRequestDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface SocialService {
    Long registerSocial(SocialSaveRequestDto dto);

    Optional<Social> getSocialTypeAndEmail(SocialSaveRequestDto dto);

    String getKakaoAccessToken(String code);

    SocialSaveRequestDto getKakaoUserInfo(String token) throws Exception;
}
