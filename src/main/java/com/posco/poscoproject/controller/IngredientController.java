package com.posco.poscoproject.controller;

import com.posco.poscoproject.entity.Branch;
import com.posco.poscoproject.entity.BranchInventory;
import com.posco.poscoproject.enumtype.Authority;
import com.posco.poscoproject.service.BranchInventoryService;
import com.posco.poscoproject.service.IngredientService;
import com.posco.poscoproject.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;
    private final BranchInventoryService branchInventoryService;
    private final BranchService branchService;

    @GetMapping({"/test/ingredientList", "/test/ingredientList/{branchId}"})
    public String ingredientList(Principal principal, Model model, @RequestParam(required = false) Long branchId) {

        // 현재 로그인한 사용자의 지점 정보
        Branch branch = branchService.findBranchByEmail(principal.getName());
        model.addAttribute("user", branch);

        // 모든 지점의 목록 가져오기
        List<Branch> branches = branchService.findAllBranches();
        model.addAttribute("branches", branches); // 모델에 지점 목록 추가
        model.addAttribute("selectedBranchId", branchId);

        //재고 목록
        List<BranchInventory> list = null;

        //본점과 지점이 보는 주문목록이 다름
        if (branch.getAuthority().equals(Authority.ADMIN)) {
            if (branchId != null) {
                // 주어진 branchId에 대한 재고 목록
                list = branchInventoryService.ingredientListByBranch(branchId);
            }
        } else {
            //지점 재고 목록
            Long myBranchId = branch.getBranchId();
            list = branchInventoryService.ingredientListByBranch(myBranchId);
        }

        model.addAttribute("list", list);

        return "test/ingredientList";
    }
}
