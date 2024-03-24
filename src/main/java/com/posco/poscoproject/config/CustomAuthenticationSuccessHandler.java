package com.posco.poscoproject.config;

import com.posco.poscoproject.service.BranchService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private BranchService branchService; // BranchService 주입

    // 생성자를 통해 BranchService 주입

    public CustomAuthenticationSuccessHandler(BranchService branchService) {
        this.branchService = this.branchService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String username = authentication.getName(); // 사용자 이름(이메일)을 기반으로

        System.out.println("findBranchIdByUsername is called with username: " + username);
        Long branchId = branchService.findBranchIdByUsername(username); // BranchService를 사용해 branchId 조회

        HttpSession session = request.getSession(); // 세션 가져오기
        session.setAttribute("branchId", branchId); // 세션에 branchId 저장

        System.out.println("Logged in user branch ID: " + session.getAttribute("branchId"));

        response.sendRedirect("/main"); // 홈 페이지로 리디렉션
    }


}
