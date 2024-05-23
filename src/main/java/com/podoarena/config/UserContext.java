package com.podoarena.config;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter
public class UserContext extends User {
    //authentication 객체에 저장하고 싶은 값을 필드로 지정
    private final String name;

    public UserContext(com.podoarena.entity.Member member, List<GrantedAuthority> authorities) {
        //User 생성자 실행
        super(member.getEmail(), member.getPassword(), authorities);
        // 추가 내용 입력
        this.name = member.getName();
    }
}
