package com.posco.poscoproject.controller;

import com.posco.poscoproject.entity.Branch;
import com.posco.poscoproject.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import com.posco.poscoproject.dto.BranchFormDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;
    private final PasswordEncoder passwordEncoder;

    //로그로 내용확인을 위한 객체 추가
    private static final Logger logger = LoggerFactory.getLogger(BranchController.class);

    @GetMapping({"/test/branchList", "/test/branchList/{page}"})
    public String branchManage(Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC)
    Pageable pageable, @RequestParam(value = "searchTerm", required = false) String searchTerm, Principal principal){

        //nav 유저 표시
        Branch branch = branchService.findBranchByEmail(principal.getName());
        model.addAttribute("user", branch);

        //지점목록 검색 결과 또는 전체 목록
        Page<Branch> list;
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            list = branchService.searchByBranchName(searchTerm, pageable);
        } else {
            list = branchService.branchList(pageable);
        }
        model.addAttribute("list", list);
        // 검색어 유지
        model.addAttribute("searchTerm", searchTerm);

        //페이징 변수
        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 9, list.getTotalPages());
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "test/branchList";
    }

    @GetMapping("/test/branchUpdate/{branchId}")
    public String update(@PathVariable("branchId") Long branchId, Model model, Principal principal){

        //nav 유저 표시
        Branch branch = branchService.findBranchByEmail(principal.getName());
        model.addAttribute("branch", branchService.findById(branchId));
        model.addAttribute("user", branch);

        return "test/branchUpdate";
    }

    @PutMapping("/test/branchUpdate/{id}")
    public String update(@PathVariable("id") Long id, Branch branch, Principal principal, Model model) {
        //testBranch 내용 확인
        logger.info("Updating branch with id: {}, {}", id, branch.toString());
        branchService.savePost(branch, id);
        model.addAttribute("user", branchService.findBranchByEmail(principal.getName()));

        return "redirect:/test/branchList";
    }

    @GetMapping("/test/branchForm")
    public String branchForm(Model model, Principal principal){

        //nav 유저 표시
        Branch navBranch = branchService.findBranchByEmail(principal.getName());
        model.addAttribute("user", navBranch);

        model.addAttribute("branchFormDTO", new BranchFormDTO());

        return "/test/branchForm";
    }

    @PostMapping("/test/branchForm")
    public String newBranch(@Valid BranchFormDTO branchFormDTO, BindingResult bindingResult, Model model, Principal principal){

        //nav 유저 표시
        Branch navBranch = branchService.findBranchByEmail(principal.getName());
        model.addAttribute("user", navBranch);

        if(bindingResult.hasErrors()){
            System.out.println("입력오류");
            return "/test/branchForm";
        }

        //testBranchFormDTO 내용 확인
        logger.info("branchFormDTO with branchName: {}, email: {}, address: {}, phone: {}",
                branchFormDTO.getBranchName(),
                branchFormDTO.getEmail(),
                branchFormDTO.getAddress(),
                branchFormDTO.getPhone());

        try {
            Branch branch = Branch.createBranch(branchFormDTO, passwordEncoder);
            branchService.saveBranch(branch);
            System.out.println("지점등록 성공!");
        } catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            System.out.println("예외 발생");
            return "/test/branchForm";
        }

        return "redirect:/test/branchList";
    }
}
