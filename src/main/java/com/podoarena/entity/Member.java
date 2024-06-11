package com.podoarena.entity;

import com.podoarena.constant.Role;
import com.podoarena.dto.MemberFormDto;
import com.podoarena.repository.MemberRepository;
import com.podoarena.service.MemberService;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Entity
@Table(name = "member")
@Getter
@Setter
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

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cart> cart;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GoodsCart> goodsCart;

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

    public static void resetPassword(Member member, String password, PasswordEncoder passwordEncoder) {
        String resetPassword = passwordEncoder.encode(password);
        member.setPassword(resetPassword);
    }

    public static boolean confirmPassword(Member member, String password, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(password, member.getPassword());
    }
}
