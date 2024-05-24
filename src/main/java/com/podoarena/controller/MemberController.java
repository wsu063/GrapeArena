package com.podoarena.controller;

import com.podoarena.entity.Member;
import com.podoarena.repository.MemberRepository;
import com.podoarena.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    //로그인 페이지 이동
    @GetMapping(value = "/members/login")
    public String loginpage() {
        return "member/memberLoginForm";
    }

    //회원가입 페이지 이동
    @GetMapping(value = "/members/register")
    public String registerPage() {
        return "member/memberForm";
    }

    //이메일 휴대폰 번호 검사
    @PostMapping(value = "/members/chkuser")
    public ResponseEntity<String> chkUser(@RequestBody Map<String, String> requestData) throws Exception {
        String email = requestData.get("email");
        String phone = requestData.get("phone");
        boolean chkUser = memberService.chkUser(email, phone);

        if(chkUser) {
            return new ResponseEntity<String>(email, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //비밀번호 재설정
    @PostMapping(value = "/members/resetpassword")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> requestData) {
        String resetPassword = requestData.get("resetPaswword");
        String resetChk = requestData.get("resetChk");
        String email = requestData.get("email");

        boolean confirmPassword = resetPassword.equals(resetChk);

        if(confirmPassword) {
            Member member = memberRepository.findByEmail(email);
            member.resetPassword(member, resetPassword, passwordEncoder);
            memberRepository.save(member);
            return new ResponseEntity<String>(email, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("비밀번호가 일치 하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
    }
}