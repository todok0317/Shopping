package com.example.shopping.controller;

import com.example.shopping.dto.MemberDto;
import com.example.shopping.entity.member.Member;
import com.example.shopping.service.LoginService;
import com.example.shopping.service.MailService;
import com.example.shopping.session.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final MailService mailService;

    // 로그인 페이지를 반환하면서 member 객체를 모델에 추가
    @GetMapping("/login")
    public String loginGet(Model model) {
        model.addAttribute("member", new Member());
        return "login/loginPage";
    }

    @PostMapping("/login")
    public String loginPost (@Valid @ModelAttribute("member") Member member, BindingResult result, HttpServletRequest request) {

        Member loginMember = loginService.login(member);
        if(loginMember == null) {
            result.rejectValue("password1", "MisMath", "아이디 또는 비밀번호가 일치하지 않습니다.");
        }

        if (result.hasErrors()) {
            return "login/loginPage";
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        return "redirect:/" + loginMember.getRole();
    }

    @PostMapping("/logout")
    public String logout (HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    @GetMapping("/findId")
    public String findId (@ModelAttribute("member")MemberDto memberDto) {
        return "login/help/findId";
    }

    @PostMapping("/findId")
    public String findIdPost (MemberDto memberDto, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "login/help/findId";
        }
        List<String> findIds = loginService.findById(memberDto.getEmail());
        redirectAttributes.addAttribute("findIds", findIds);

        return "redirect:/findId/findIdCom";
    }

    @GetMapping("/findId/findIdCom")
    public String findIdCom (@RequestParam("findIds") List<String> findIds, Model model) {
        model.addAttribute("findIds", findIds);
        return "login/help/findIdCom";
    }

    @PostMapping("/login/mailComfirm")
    @ResponseBody
    public String mailConfirm (@RequestParam("email") String email, HttpSession session) throws Exception {
        String code = mailService.sendSimpleMessage(email);
        System.out.println("인증코드 : " + code);

        session.setAttribute("code", code);
        return code;
    }

    @GetMapping("/findPwd")
    public String findPwdGet(@ModelAttribute("member") MemberDto memberDto) {
        return "login/help/findPwd";
    }

    @PostMapping("/findPwd")
    public String findPwdPost (MemberDto memberDto, RedirectAttributes redirectAttributes) {
        String password = loginService.findPassword(memberDto.getUsername(), memberDto.getLoginId());

        if (password != null) {
            String encryptedPassword = Base64.getEncoder().encodeToString(password.getBytes(StandardCharsets.UTF_8));
            redirectAttributes.addAttribute("password", encryptedPassword);
        } else if (password == null) {
            redirectAttributes.addAttribute("password", null);
        }
        return "redirect:/findPwd/findPwdCom";
    }

    @GetMapping("/findPwd/findPwdCom")
    public String findIdCom (@RequestParam(value = "password", required = false) String encryptedPassword, Model model) {

        if(encryptedPassword != null) {
            String originalPassword = new String(Base64.getDecoder().decode(encryptedPassword), StandardCharsets.UTF_8);
            model.addAttribute("password", originalPassword);
        } else if (encryptedPassword == null) {
            model.addAttribute("password", null);
        }

        return "login/help/findPwdCom";
    }

}
