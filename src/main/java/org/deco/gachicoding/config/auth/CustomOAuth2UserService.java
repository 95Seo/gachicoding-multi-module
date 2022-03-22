package org.deco.gachicoding.config.auth;

import lombok.RequiredArgsConstructor;
import org.deco.gachicoding.config.auth.dto.OAuthAttributes;
import org.deco.gachicoding.config.auth.dto.SessionUser;
import org.deco.gachicoding.domain.user.User;
import org.deco.gachicoding.domain.user.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // 로그인 진행중인 서비스를 구분하는 코드

        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

//      OAuth2UserService 를 통해 얻은 정보를 담는 클래스, 소셜 로그인 시 제공받은 회원의 정보를 담음.
        OAuthAttributes attributes =  OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRole().getKey())), attributes.getAttributes(), attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.socialUpdate(attributes.getName(), attributes.getEmail()))
                .orElse(attributes.toEntity());

//        User user = userRepository.findByEmail(attributes.getEmail())
//                .map(entity -> entity.socialUpdate(attributes.getName()));
////                .orElse(attributes.toEntity())
        return userRepository.save(user);
    }
}
