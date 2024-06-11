package com.podoarena.service;

import com.podoarena.dto.MemberFormDto;
import com.podoarena.entity.Member;
import com.podoarena.entity.Reserve;
import com.podoarena.repository.MemberRepository;
import com.podoarena.repository.ReserveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final ReserveRepository reserveRepository;

    //회원가입
    public Member saveMember(Member member) {
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    //회원중복체크
    private void validateDuplicateMember(Member member) {
        Member findMember = memberRepository.findByEmail(member.getEmail());
        Member findPhone = memberRepository.findByPhone(member.getPhone());

        if(findMember != null) {
            throw new IllegalStateException("이미 가입된 이메일 입니다.");
        } else if (findPhone != null) {
            throw new IllegalStateException("이미 가입된 번호 입니다.");
        }
    }

    //이메일과 휴대폰 번호로 일치 확인
    public boolean chkUser(String email, String phone) {
        Member findPassword = memberRepository.findByEmailAndPhone(email, phone);
        if(findPassword == null) {
            throw new IllegalStateException("잘못된 정보입니다. 확인 후 다시 입력해주세요.");
        }
        return true;
    }

    //아이디 찾기 boolean
    public boolean findId(String phone, String name) {
        Member findName = memberRepository.findByName(name);
        Member findPhone = memberRepository.findByPhone(phone);
        if(findName == null) {
            throw new IllegalStateException("가입되지 않은 이름입니다. 확인 후 다시 입력해주세요.");
        } else if(findPhone == null) {
            throw new IllegalStateException("가입되지 않은 번호입니다. 확인 후 다시 입력해주세요.");
        }

        return true;
    }

    public Member getMember(String email) {return memberRepository.findByEmail(email); }

    //entity -> dto
    public MemberFormDto loadMember(String email) {
        Member member = memberRepository.findByEmail(email);
        MemberFormDto memberFormDto = MemberFormDto.of(member);
        return memberFormDto;
    }

    // 회원정보수정
    public void editMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        //1. MemberFormDto를 입력받아서
        //2. 수정된 member를 저장한다.
        Member member = memberRepository.findByEmail(memberFormDto.getEmail());

        if(member != null) {
            if(!memberFormDto.getPassword().equals("true")) { //비밀번호 수정을 하면 바꿔주고
                String password = passwordEncoder.encode(memberFormDto.getPassword());
                member.setPassword(password);
            } //수정을 안하면 setPassword를 안한다.
            member.setPhone(memberFormDto.getPhone());
            member.setPostcode(memberFormDto.getPostcode());
            member.setAddress(memberFormDto.getAddress());
            member.setDetailAddress(memberFormDto.getDetailAddress());
            member.setExtraAddress(memberFormDto.getExtraAddress());
            memberRepository.save(member);
        }
    }

    public void confirmDelete(String email) {
        Member member = memberRepository.findByEmail(email);
        memberRepository.delete(member);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //해당 email 계정을 가진 사용자가 있는지 확인
        Member member = memberRepository.findByEmail(email);

        if(member == null) { // 사용자가 없다면
            throw new UsernameNotFoundException(email);
        }
        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles((member.getRole().toString()))
                .build();
    }


}
