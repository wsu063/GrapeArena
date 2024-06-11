package com.podoarena.controller;

import com.podoarena.dto.MemberFormDto;
import com.podoarena.entity.Member;
import com.podoarena.entity.Reserve;
import com.podoarena.repository.MemberRepository;
import com.podoarena.repository.ReserveRepository;
import com.podoarena.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final ReserveRepository reserveRepository;

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
            Reserve reserve = new Reserve();
            reserve.setMember(member);
            reserveRepository.save(reserve);

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
    @PostMapping(value = "/members/resetpw")
    public ResponseEntity<String> resetpwpage(@RequestBody  Map<String, String> requestData) {
        String email = requestData.get("email");
        String resetPw = requestData.get("resetPw");
        String resetPwChk = requestData.get("resetPwChk");

        boolean comparePw = resetPw.equals(resetPwChk);

        if(comparePw) {
            Member member = memberRepository.findByEmail(email);
            member.resetPassword(member, resetPw, passwordEncoder);
            memberRepository.save(member);
            return new ResponseEntity<String>(email, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
    }

    //아이디 찾기 페이지
    @GetMapping(value = "/members/findid")
    public String findIdPage(Model model) {
        model.addAttribute("member", new MemberFormDto());
        return "member/findid";
    }

    //찾은 아이디 표시
    @PostMapping(value = "/members/showid")
    public ResponseEntity<String> findId(@RequestBody Map<String, String> requestData, Model model){
        String name = requestData.get("name");
        String phone = requestData.get("phone");


        Member findId = memberRepository.findByPhoneAndName(phone, name);
        String email = findId.getEmail();

        if(findId != null) {
            return new ResponseEntity<String>(email, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("해당 정보로 가입한 내역이 없습니다.", HttpStatus.BAD_REQUEST);
        }
    }

    //마이페이지 전 페이지
    @GetMapping(value = "/members/mypage")
    public String myPageConfirm(Model model, Principal principal) {
        try {
            if (principal.getName() != null) {
                model.addAttribute("member", memberService.getMember(principal.getName()));
                return "member/mypage";
            }
        } catch (Exception e) {
            return "member/login";
        }
        return "member/mypage";
    }

    //마이페이지 비밀번호 확인
    @PostMapping(value = "/members/mypage")
    public String myPage(@RequestParam("password")String password, Principal principal, Model model) {
        Member member = memberRepository.findByEmail(principal.getName());
        boolean confirmPassword = member.confirmPassword(member, password, passwordEncoder);
        if(confirmPassword) {
            return "redirect:/members/editprofile";
        } else {
            model.addAttribute("error", "비밀번호가 일치하지 않습니다.");
            return "member/mypage";
        }
    }

    @GetMapping(value = "/members/editprofile")
    public String editProfilePage(Principal principal, Model model) {
        if(principal == null) {
            return "redirect:/members/login";
        } else {
            String email = principal.getName();
            MemberFormDto member = memberService.loadMember(email);
            model.addAttribute("member", member);
            return "member/editprofile";
        }
    }

    @PostMapping(value = "/members/editprofile")
    public String editProfile(Model model, MemberFormDto memberFormDto) {
        memberService.editMember(memberFormDto, passwordEncoder);
        return "redirect:/";
    }

    @DeleteMapping(value = "/members/deleteMember")
    public @ResponseBody ResponseEntity deleteMember(@RequestBody Map<String, String> requestData) {
        String email = requestData.get("email");
        String deleteMemberText = requestData.get("deleteMemberText");
        if (deleteMemberText.equals("탈퇴")) {
            memberService.confirmDelete(email);
           return new ResponseEntity<String>("성공", HttpStatus.OK);
        } else {
            return  new ResponseEntity<String>("error", HttpStatus.BAD_REQUEST);
        }

    }


    @GetMapping(value = "/members/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg","아이디 또는 비밀번호를 확인 해주세요. ");
        return "member/login";

    }
}