package com.podoarena.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.podoarena.dto.MemberFormDto;
import com.podoarena.entity.CustomOAuth2User;
import com.podoarena.entity.Member;
import com.podoarena.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(request);
        String oauthClientName = request.getClientRegistration().getClientName();


//        try {
//            System.out.println(new ObjectMapper().writeValueAsString(oAuth2User.getAttributes()));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        Member member = null;
        String providerId = null;
        String email = "email@email.com";

        if(oauthClientName.equals("kakao")) {
            providerId= "kakao_" + oAuth2User.getAttributes().get("id");
            member = new Member(providerId, email, "kakao");
        }

        if(oauthClientName.equals("naver")) {
            Map<String, String> responseMap = (Map<String, String>)oAuth2User.getAttributes().get("response");
            providerId = "naver_" + responseMap.get("id").substring(0,14);
            email = responseMap.get("email");
            member = new Member(providerId, email, "naver" );
        }

        memberRepository.save(member);

        return new CustomOAuth2User(providerId);
    }

}
