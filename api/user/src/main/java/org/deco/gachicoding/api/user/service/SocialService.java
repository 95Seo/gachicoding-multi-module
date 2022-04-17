package org.deco.gachicoding.api.user.service;

import org.deco.gachicoding.api.user.dto.SocialSaveRequestDto;
import org.deco.gachicoding.core.common.domain.Social;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface SocialService {
    Long registerSocial(SocialSaveRequestDto dto);

    Optional<Social> getSocialTypeAndEmail(SocialSaveRequestDto dto);

    String getKakaoAccessToken(String code);

    SocialSaveRequestDto getKakaoUserInfo(String token) throws Exception;
}
