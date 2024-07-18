package project.realestate.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    // nickname 을 session 에 저장
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();

        // 현재 인증된 사용자의 정보를 가져옴
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        session.setAttribute("nickname", userDetails.getNickname());
        session.setAttribute("role", userDetails.getRole());

        // 로그, 잘 넘어옴
        //System.out.println("userDetails = " + userDetails.getNickname());
        //System.out.println("userDetails = " + userDetails.getRole());

        response.sendRedirect("/");
    }
}
