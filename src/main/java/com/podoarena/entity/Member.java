package com.podoarena.entity;

import com.podoarena.constant.Role;
import com.podoarena.dto.MemberFormDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "member")
@Getter
@Setter
@ToString
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

    private String address;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cart cart;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private Reserve reserve;


    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        String password = passwordEncoder.encode(memberFormDto.getPassword());

        Member member = new Member();

        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setPassword(password);
        member.setAddress(memberFormDto.getAddress());
        member.setPhone(memberFormDto.getPhone());

        //member.setRole(Role.USER);
        member.setRole(Role.ADMIN);

        return member;
    }

    public static void resetPassword(Member member, String password, PasswordEncoder passwordEncoder) {
        String resetPassword = passwordEncoder.encode(password);
        member.setPassword(resetPassword);
    }
}
