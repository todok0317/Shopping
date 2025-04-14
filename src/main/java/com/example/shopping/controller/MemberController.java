package com.example.shopping.controller;

import com.example.shopping.dto.MemberDto;
import com.example.shopping.service.JoinMailServiceImpl;
import com.example.shopping.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final JoinMailServiceImpl joinMailService;

    // 회원가입 폼
    @GetMapping("/join")
    public String joinGet(@ModelAttribute("member") MemberDto memberDto){

        // @ModelAttribute("member")은
        // MemberDto 객체를 자동으로 모델에 추가해서 Thymeleaf에서 사용 가능하게 만듦.

        // resources/templates/members/join.html
        return "members/join";
    }

    // 회원가입 처리
    @PostMapping("/join")
    public String joinPost (@Valid MemberDto memberDto, BindingResult bindingResult) {

        // BindingResult bindingResult는 @Valid의 검증 결과를 담고 있는 객체
        // 오류가 있으면 bindingResult.hasErrors()가 true가 됨.


        // 유효성 검사 실패하면 다시 회원가입 폼으로 돌아감
        // true인게 비밀번호가 비었거나, 이메일 형식이 다르거나 등등
        if(bindingResult.hasErrors()) {
            return "members/join"; // 에러가 있으면 회원가입 페이지로 돌아감
        }

        // 회원가입 진행 (DB 저장)
        memberService.join(memberDto);
        // 성공하면 완료 페이지로 이동
        return "members/complete";
    }

    // 아이디 중복검사 API
    @PostMapping("/checkDuplicateId")
    @ResponseBody
    public int checkDuplicateId (@RequestParam("loginId") String loginId) {
        int id = memberService.validateMemberId(loginId);
        return id;
    }

    //비밀번호 일치검사 API
    @PostMapping("/checkMatchPassword")
    @ResponseBody
    public int chackMatchPassword (@RequestParam("password1") String password1, @RequestParam("password2") String password2) {
        int password = memberService.passwordMatch(password1, password2);
        return password;
    }

    // 이메일 인증
    @PostMapping("/join/mailConfirm")
    @ResponseBody
    public String mailConfirm (@RequestParam("email") String email) throws Exception {
        String code = joinMailService.sendSimpleMessage(email);
        System.out.println("인증코드 : " + code);
        return code;
    }













}
