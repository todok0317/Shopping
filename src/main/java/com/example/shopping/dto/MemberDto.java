package com.example.shopping.dto;

import com.example.shopping.entity.Address;
import com.example.shopping.entity.Role;
import com.example.shopping.entity.member.Member;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Getter @Setter
@NoArgsConstructor
public class MemberDto {

    private Long id;

    @NotBlank(message = "아이디를 입력하세요.")
    private String loginId;

    @NotBlank(message = "이름을 입력하세요.")
    private String username;

    @NotBlank(message = "비밀번호를 입력하세요")
    private String password1;

    @NotBlank(message = "비밀번호를 다시한번 입력해주세요")
    private String password2;

    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @NotBlank(message = "생년월일 8자리를 입력해주세요.")
    private String birthDay;

    private Role role;

    private Address address;

    @Builder
    public MemberDto( String loginId, String username, String password1, String password2, String email, String birthDay, Role role, Address address){
        this.loginId = loginId;
        this.username = username;
        this.password1 = password1;
        this.password2 = password2;
        this.email = email;
        this.birthDay = birthDay;
        this.role = role;
        this.address = address;
    }

    // 실제 entity 객체를 가져오는 메소드
    public Member toEntity (MemberDto memberDto) {
        Member entity = Member.builder()
                .loginId(memberDto.loginId)
                .username(memberDto.username)
                .password1(memberDto.password1)
                .password2(memberDto.password2)
                .email(memberDto.email)
                .birthDay(memberDto.birthDay)
                .role(memberDto.role)
                .address(memberDto.address)
                .build();

        return entity;
    }

    // 실제 객체를 dto로 변환하는 메서드
    public MemberDto of (Member member) {
        MemberDto dto = MemberDto.builder()
                .loginId(member.getLoginId())
                .username(member.getUsername())
                .password1(member.getPassword1())
                .password2(member.getPassword2())
                .email(member.getEmail())
                .birthDay(member.getBrithDay())
                .role(member.getRole())
                .address(member.getAddress())
                .build();

        return dto;
    }
}
