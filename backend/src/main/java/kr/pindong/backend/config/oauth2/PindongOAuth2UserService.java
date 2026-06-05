package kr.pindong.backend.config.oauth2;

import kr.pindong.backend.domain.user.OAuthAccount;
import kr.pindong.backend.domain.user.OAuthAccountRepository;
import kr.pindong.backend.domain.user.User;
import kr.pindong.backend.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PindongOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final OAuthAccountRepository oauthAccountRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String provider = userRequest.getClientRegistration().getRegistrationId();
        String providerUid = attributes.get("sub").toString();
        String email = attributes.get("email").toString();
        String name = attributes.get("name").toString();
        String picture = attributes.get("picture").toString();

        User user = oauthAccountRepository.findByProviderAndProviderUid(provider, providerUid)
                .map(OAuthAccount::getUser)
                .orElseGet(() -> findOrCreateUser(provider, providerUid, email, name, picture));

        return new PindongOAuth2User(user, attributes);
    }

    private User findOrCreateUser(String provider, String providerUid, String email, String name, String picture) {
        User user = userRepository.findByEmail(email)
                .orElseGet(() -> userRepository.save(User.builder().email(email).nickname(name).profileImage(picture).build()));

        oauthAccountRepository.save(OAuthAccount.builder().user(user).provider(provider).providerUid(providerUid).build());

        return user;
    }
}
