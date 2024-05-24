package com.podoarena.service;

import com.podoarena.entity.Member;
import com.podoarena.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;

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
        Member findEmail = memberRepository.findByEmail(email);
        Member findPhone = memberRepository.findByPhone(phone);
        if(findEmail == null) {
            throw new IllegalStateException("가입되지 않은 이메일입니다. 확인 후 다시 입력해주세요.");
        } else if(findPhone == null) {
            throw new IllegalStateException("가입되지 않은 번호입니다. 확인 후 다시 입력해주세요.");
        }

        return true;
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
