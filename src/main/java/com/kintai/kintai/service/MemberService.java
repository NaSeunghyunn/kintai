package com.kintai.kintai.service;

import com.kintai.kintai.repository.MemberCompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {

    private final MemberCompanyRepository memberCompanyRepository;

    @Transactional(readOnly = true)
    public List<String> findCompanyNames(Long memberId) {
        return memberCompanyRepository.findCompanyNames(memberId);
    }
}
