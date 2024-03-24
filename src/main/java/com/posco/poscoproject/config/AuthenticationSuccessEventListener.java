package com.posco.poscoproject.config;

import com.posco.poscoproject.service.BranchService;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {
    private HttpSession httpSession;
    private final BranchService branchService;

    public AuthenticationSuccessEventListener(BranchService branchService, HttpSession httpSession) {
        this.branchService = branchService;
        this.httpSession = httpSession;
    }
    // httpSession 에 branchId를 저장하기 위한 클래스
    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        String username = event.getAuthentication().getName();
        Long branchId = branchService.findBranchIdByUsername(username);
        String branchName = branchService.findBranchNameByUsername(username);
        httpSession.setAttribute("branchId", branchId);
        httpSession.setAttribute("branchName", branchName);
        System.out.println("--------------------------------------------------------------" + branchId);
        System.out.println("--------------------------------------------------------------" + branchName);
    }
}
