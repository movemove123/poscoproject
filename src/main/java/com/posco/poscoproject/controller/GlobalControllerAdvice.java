package com.posco.poscoproject.controller;
import com.posco.poscoproject.entity.Branch;
import com.posco.poscoproject.service.BranchService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;
import java.security.Principal;

@ControllerAdvice
public class GlobalControllerAdvice {

    private final BranchService branchService;

    public GlobalControllerAdvice(BranchService branchService) {
        this.branchService = branchService;
    }

    @ModelAttribute
    public void addAttributes(Principal principal, Model model) {
        if (principal != null) {
            Branch branch = branchService.findBranchByEmail(principal.getName());
            model.addAttribute("user", branch);
        }
    }
}

