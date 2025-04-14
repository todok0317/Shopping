package com.example.shopping.repository;

import com.example.shopping.entity.member.Member;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LoginFindRepositoryImpl implements LoginRepository{

    private final EntityManager em;

    public LoginFindRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Member> findById(String id) {
        String jpql = "select m from Member m where m.loginId =: loginId";
        List<Member> loginId = em.createQuery(jpql, Member.class).setParameter("loginId", id).getResultList();
        return loginId;
    }

    @Override
    public List<Member> findBPwd(String pwd) {
        return List.of();
    }

    public List<Member> findPwd (String username, String loginId) {
        String jpql = "select m from Member m where m.username=:username and m.loginId=:loginId";
        List<Member> password = em.createQuery(jpql, Member.class)
                .setParameter("username", username)
                .setParameter("loginId", loginId).getResultList();
        return password;
    }
}
