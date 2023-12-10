package com.kintai.kintai.service;

import com.kintai.kintai.auth.LoginMember;
import com.kintai.kintai.domain.entity.Member;
import com.kintai.kintai.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class LoginService {
    private final MemberRepository memberRepository;

    public LoginMember login(String name) {
        Member member = memberRepository.findByName(name);
        return member == null ? null : new LoginMember(member.getId(), member.getName());
    }
}
