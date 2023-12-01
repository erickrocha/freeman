package com.erocha.freeman.commons.security.oauth2;

import com.erocha.freeman.commons.domains.CustomUserDetails;
import com.erocha.freeman.security.domains.User;
import com.erocha.freeman.security.gateways.UserGateway;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final List<OAuth2UserInfoExtractor> oAuth2UserInfoExtractors;


    private final UserGateway userGateway;

    public CustomOAuth2UserService(List<OAuth2UserInfoExtractor> oAuth2UserInfoExtractors, UserGateway userGateway) {
        this.oAuth2UserInfoExtractors = oAuth2UserInfoExtractors;
        this.userGateway = userGateway;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        Optional<OAuth2UserInfoExtractor> oAuth2UserInfoExtractorOptional = oAuth2UserInfoExtractors.stream()
                .filter(oAuth2UserInfoExtractor -> oAuth2UserInfoExtractor.accepts(userRequest))
                .findFirst();
        if (oAuth2UserInfoExtractorOptional.isEmpty()) {
            throw new InternalAuthenticationServiceException("The OAuth2 provider is not supported yet");
        }

        CustomUserDetails customUserDetails = oAuth2UserInfoExtractorOptional.get().extractUserInfo(oAuth2User);
        User user = upsertUser(customUserDetails);
        customUserDetails.setId(user.getId());
        return customUserDetails;
    }

    private User upsertUser(CustomUserDetails customUserDetails) {
        Optional<User> userOptional = userGateway.findByEmail(customUserDetails.getEmail());
        User user;
        if (userOptional.isEmpty()) {
            user = new User();

            user.setName(customUserDetails.getName());
            user.setEmail(customUserDetails.getEmail());
//            user.setImageUrl(customUserDetails.getAvatarUrl());
//            user.setProvider(customUserDetails.getProvider());
//            usersetRole(WebSecurityConfig.USER);
        } else {
            user = userOptional.get();
            user.setEmail(customUserDetails.getEmail());
//            user.setImageUrl(customUserDetails.getAvatarUrl());
        }
        return userGateway.persist(user);
    }
}
