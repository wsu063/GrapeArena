package com.podoarena.entity;

import com.podoarena.constant.Role;
import com.podoarena.dto.MemberFormDto;
import com.podoarena.repository.MemberRepository;
import com.podoarena.service.MemberService;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Entity
@Table(name = "member")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity {
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String password;

    @Column(unique = true)
    private String email;

    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(unique = true)
    private String phone;

    private String postcode;

    private String address;

    private String detailAddress;

    private String extraAddress;

    //sns 네이버 카카오 구글 등
    private String provider;
    //SNS 로그인 유저 고유ID
    private String providerId;


    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cart cart;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private Reserve reserve;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Orders> ordersList;


    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        String password = passwordEncoder.encode(memberFormDto.getPassword());

        Member member = new Member();

        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setPostcode(memberFormDto.getPostcode());
        member.setPassword(password);
        member.setAddress(memberFormDto.getAddress());
        member.setDetailAddress(memberFormDto.getDetailAddress());
        member.setExtraAddress(memberFormDto.getExtraAddress());
        member.setPhone(memberFormDto.getPhone());

        //member.setRole(Role.USER);
        member.setRole(Role.ADMIN);

        return member;
    }

    //비밀번호 재설정
    public static void resetPassword(Member member, String password, PasswordEncoder passwordEncoder) {
        String resetPassword = passwordEncoder.encode(password);
        member.setPassword(resetPassword);
    }

    //비밀번호 확인
    public static boolean confirmPassword(Member member, String password, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(password, member.getPassword());
    }
}
