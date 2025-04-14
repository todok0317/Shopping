package com.example.shopping.repository;

import com.example.shopping.entity.member.Member;

import java.util.List;

public interface LoginRepository {

    // 객체에서 아이디값을 찾는 메서드
    List<Member> findById(String id);

    // 객체에서 비밀번호 값을 찾는 메서드
    List<Member> findBPwd(String pwd);

}
