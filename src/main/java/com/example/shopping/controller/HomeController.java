package com.example.shopping.controller;

import com.example.shopping.entity.member.Member;
import com.example.shopping.session.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static com.example.shopping.entity.Role.user;

@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/")
    public String defaultHome() {
        return "home";
    }

    @GetMapping("/{role}")
    public String loginHoe (HttpServletRequest request, @PathVariable("role") String role, Model model) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            return "home";
        }

        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        if(loginMember == null) {
            return "home";
        }
        if (role.equals(String.valueOf(user))) {
            model.addAttribute("member", loginMember);
            return "userHome";
        }
        model.addAttribute("member", loginMember);

        return "loginHome";
    }


}
