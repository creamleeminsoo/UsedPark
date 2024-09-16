package me.leeminsoo.usedpark.config.oauth2;

import lombok.RequiredArgsConstructor;

import me.leeminsoo.usedpark.config.oauth2.memberinfo.GoogleMemberInfo;
import me.leeminsoo.usedpark.config.oauth2.memberinfo.KakaoMemberInfo;
import me.leeminsoo.usedpark.config.oauth2.memberinfo.OAuth2MemberInfo;
import me.leeminsoo.usedpark.domain.user.Role;
import me.leeminsoo.usedpark.domain.user.User;
import me.leeminsoo.usedpark.repository.user.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class OAuth2UserCustomService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        OAuth2MemberInfo memberInfo = null;

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        if (registrationId.equals("google")) {
            memberInfo = new GoogleMemberInfo(user.getAttributes());

        }else if (registrationId.equals("kakao")){
            Map<String, Object> kakaoAccount = (Map<String, Object>) user.getAttributes().get("kakao_account");
            memberInfo = new KakaoMemberInfo(kakaoAccount);
        }

        else {
            throw new OAuth2AuthenticationException("Unknown registration id: " + registrationId);
        }

        saveOrUpdate(memberInfo);
        return user;

    }

    private User saveOrUpdate(OAuth2MemberInfo memberInfo) {

        String email = memberInfo.getEmail();
        String name = memberInfo.getName();
        User user = userRepository.findByEmail(email)
                .map(entity -> entity.update(name))
                .orElse(User.builder()
                        .email(email)
                        .nickname(name)
                        .roles(List.of(Role.USER))
                        .build());

        return userRepository.save(user);
    }
}
