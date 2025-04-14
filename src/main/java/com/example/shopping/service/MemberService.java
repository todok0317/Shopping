package com.example.shopping.service;

import com.example.shopping.dto.MemberDto;
import com.example.shopping.entity.member.Member;
import com.example.shopping.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
// 트랜잭션을 자동으로 관리하는 어노테이션, 메서드 실행 중 예외가 발생하면 자동으로 롤백(되돌리기) 됨.
// join() 메서드가 실행될 때 성공하면 DB에 저장(Commit), 실패하면 변경사항을 되돌림(롤백)
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    // 회원가입 폼
    public void join (MemberDto dto) {
        Member entity = dto.toEntity(dto);
        memberRepository.save(entity);
    }

    // 아이디 중복
    public int validateMemberId (String id) {
        List<Member> findId = memberRepository.findById(id);

        if (findId != null && !findId.isEmpty()) {
            return 1; // 아이디 중복 시 1;
        }
        return 0; // 아이디 중복 아니면 0;
    }

    // 비밀번호 일치검사
    public int passwordMatch (String password1, String password2) {
        if(password1.equals(password2)) {
            return 0; // 비밀번호가 같다면 0
        }
        return 1; // 일치하지 않으면 1
    }




}
