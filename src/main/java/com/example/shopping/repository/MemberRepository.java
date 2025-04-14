package com.example.shopping.repository;

import com.example.shopping.entity.member.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    // JpaRepository는 트랜잭션을 자동으로 처리하는데.
    // JpaRepository가 아니라 EntityManager를 사용하는 이유는 복잡한 트랜잭션 관리에는 더 유리해서
    private final EntityManager em;

    // 회원가입 성공
    public void save(Member member) {

        // 영속성 컨텍스트로 거치고 꼭
        // 영속성 컨텍스트 (Persistence Context)에 저장
        // 즉 DB에 바로 저장되지 않고 1차 캐시에 저장됨.
        em.persist(member);
        // 영속성 컨텍스트에 있는 변경 내용을 즉시 DB에 반영
        em.flush();

    }

    // 아이디 중복검사
    public List<Member> findById(String loginId) {

        // JPQL쿼리문 SQL과 비슷하지만, 테이블이 아니라 "엔티티 객체"를 대상으로 하는 쿼리
        // 즉시로딩 지연로딩을 자신 마음대로 사용할 수 있다.
        String jpql = "select m from Member m where m.loginId=:loginId";

        // em.createQuery(jpql, Member.class : jpql 쿼리를 실행하는데, 결과를 Member.class 타입으로 반환, 쿼리 결과가 Member 객체의 리스트가 됨.
        List<Member> findId = em.createQuery(jpql, Member.class).setParameter("loginId", loginId).getResultList();

        return findId;
    }









}
