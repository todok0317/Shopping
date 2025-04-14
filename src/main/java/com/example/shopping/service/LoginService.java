package com.example.shopping.service;

import com.example.shopping.entity.member.Member;
import com.example.shopping.repository.LoginFindRepositoryImpl;
import com.example.shopping.repository.LoginRepository;
import com.example.shopping.repository.LoginRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional // 트랜잭션 안에서 실행 됨, 데이터베이스에 여러 작업을 하나로 묶어서 처리할 수 있음.
@RequiredArgsConstructor
public class LoginService {

    private final LoginRepositoryImpl loginRepository;
    private final LoginFindRepositoryImpl loginFindRepository;

    public Member login(Member member) {
        List<Member> loginId = loginRepository.findById(member.getLoginId());
        List<Member> password = loginRepository.findBPwd(member.getPassword1());

        // 로그인 유효성 검사
        if(loginId.isEmpty() || password.isEmpty() || !member.getPassword1().equals(password.get(0).getPassword1())) {
            return null;
        }
        // 로그인 성공 시
        return loginId.get(0);
    }

    public List<String> findById (String email) {
        List<String> loginIds = new ArrayList<>();
        try {
            List<Member> ids = loginFindRepository.findById(email);
            for (Member id : ids) {
                loginIds.add(id.getLoginId());
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return loginIds;
    }

    public String findPassword (String username, String loginId) {
        List<Member> pwd = loginFindRepository.findPwd(username, loginId);
        if (pwd.isEmpty()) {
            return null;
        }
        return pwd.get(0).getPassword1();
    }

}
