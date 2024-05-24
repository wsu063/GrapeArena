package com.podoarena.controller;

import com.podoarena.dto.MemberFormDto;
import com.podoarena.entity.Member;
import com.podoarena.repository.MemberRepository;
import com.podoarena.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
        return "member/login";
    }

    //회원가입 페이지 이동
    @GetMapping(value = "/members/register")
    public String registerPage(Model model) {
        model.addAttribute("memberFormDto", new MemberFormDto());

        return "member/register";
    }

    //회원가입 처리
    @PostMapping(value = "/members/register")
    public String registerUser(@Valid MemberFormDto memberFormDto,
                               BindingResult bindingResult , Model model) {
        if(bindingResult.hasErrors()) return "member/register";

        try {
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(member);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/register";
        }

        return "redirect:/";
    }

    //이메일 전화번호 검사 페이지
    @GetMapping(value = "/members/chkuser")
    public String chkUserPage(Model model) {
        model.addAttribute("member", new MemberFormDto());
        return "member/chkuser";
    }

    //이메일 전화번호 체크
    @PostMapping(value = "/members/chkuser")
    public ResponseEntity<String> chkUser(@RequestBody Map<String, String> requestData) {
        try {
            String email = requestData.get("email");
            String phone = requestData.get("phone");
            boolean chkUser = memberService.chkUser(email, phone);

            if (chkUser) {
                return new ResponseEntity<String>("인증 성공", HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("인증 실패", HttpStatus.BAD_REQUEST);
            }
        } catch (IllegalStateException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
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

    @GetMapping(value = "/members/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg","아이디 또는 비밀번호를 입력해주세요. ");
        return "member/login";

    }
}