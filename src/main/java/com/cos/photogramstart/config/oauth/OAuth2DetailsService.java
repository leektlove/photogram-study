package com.cos.photogramstart.config.oauth;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OAuth2DetailsService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        //System.out.println("OAuth2 서비스 탐~");
        OAuth2User oAuth2User = super.loadUser(userRequest);
        //System.out.println(oAuth2User.getAuthorities());
        //return null;

       Map<String, Object> userInfo = oAuth2User.getAttributes();

        String username = "facebook_" + (String) userInfo.get("id");
        String password = new BCryptPasswordEncoder().encode(UUID.randomUUID().toString());
        String email = (String) userInfo.get("email");
        String name = (String) userInfo.get("name");

        User userEntity = userRepository.findByUsername(username);

        if(userEntity == null) {  // 1-1. 페이스북 최초 로그인
            User user = User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .name(name)
                    .role("ROLE_USER")
                    .build();

            //userEntity = userRepository.save(user); <- 계속 세이브 되면 안되겠죠 한번만 되야죵~

            return new PrincipalDetails(userRepository.save(user), userInfo);

        } else {  // 페이스북으로 이미 회원가입이 된자 // 1-2. 이미 로그인을 한 번 한 유저.

            return new PrincipalDetails(userEntity, userInfo);

        }

    }
}
