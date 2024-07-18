package project.realestate.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.realestate.dto.MemberDTO;
import project.realestate.security.CustomUserDetailsService;
import project.realestate.service.MemberService;


@Controller
@RequestMapping("/sign")
@RequiredArgsConstructor
@Log4j2
public class SignController {

    private final MemberService memberService;
    private final CustomUserDetailsService customUserDetailsService;

    // 로그인 페이지
    @GetMapping("/login")
    public String login(){
        return "/sign/login";
    }

    // 회원가입 페이지
    @GetMapping("/signup")
    public String signup(Model model){
        model.addAttribute("memberDTO", new MemberDTO());
        return "/sign/signup";
    }

    // 회원가입 처리
    @PostMapping("/signup")
    public String signup(@Valid MemberDTO memberDTO, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "/sign/signup";
        }
        memberService.saveMember(memberDTO);
        log.info("memberDTO : " + memberDTO);
        return "redirect:/";
    }

    // 로그인 인증 성공 후 사용자 정보를 조회하고, 세션에 저장
    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession httpSession, Model model){
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
        httpSession.setAttribute("nickname", userDetails.getUsername());

        return "redirect:/";
    }

    // 로그아웃 처리
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        // 세션에서 사용자 정보 삭제
        session.invalidate();
        return "redirect:/";
    }

}

