package com.example.shopping.entity.member;

import com.example.shopping.entity.Address;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;

import javax.management.relation.Role;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String loginId;

    private String username;

    private String password1;

    private String password2;

    private String email;
    private String birthDay;

    private String BrithDay;

    // JPA가 enum값을 어떻게 데이터베이스에 저장할지 알려주는데 STRING이니 문자열로 저장
    @Enumerated(EnumType.STRING)
    private Role role;

    // JPA에서 값 타입(임베디드 타입)을 엔티티 안에 포함시킬 때 사용.
    // Address는 엔티티가 아니라 하나의 객체로 묶인 값 타입 클래스인데 그걸 재사용할때 이렇게 사용함.
    // BaseEntity(공통적인 필드)와 다른점? 원할때만 사용가능? 특정한 값들을 모아놓은 것.
    @Embedded
    private Address address;

    @Builder
    public Member (String loginId, String username, String password1, String password2, String email, String birthDay, Role role, RabbitConnectionDetails.Address address) {
        this.loginId = loginId;
        this.username = username;
        this.password1 = password1;
        this.password2 = password2;
        this.email = email;
        this.birthDay = birthDay;
        this.role = role;
        this.address = address;
    }
}
