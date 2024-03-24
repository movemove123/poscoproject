package com.posco.poscoproject.controller;

import com.posco.poscoproject.entity.Branch;
import com.posco.poscoproject.entity.ProductOrder;
import com.posco.poscoproject.enumtype.Authority;
import com.posco.poscoproject.service.BranchService;
import com.posco.poscoproject.service.ProductOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.security.Principal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final ProductOrderService productOrderService;
    private final BranchService branchService;

//    @GetMapping({"/test/orders", "/test/orders/{page}"})
//    public String orderList(Principal principal, Model model,
//                            @RequestParam(value = "startDate", required = false, defaultValue = "1900-01-01") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
//                            @RequestParam(value = "endDate", required = false, defaultValue = "9999-12-31") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
//                            @PageableDefault(page = 0, size = 10, sort = "poDate", direction = Sort.Direction.DESC) Pageable pageable) {
//
//        //nav 유저 표시
//        TestBranch testBranch = testBranchService.findBranchByEmail(principal.getName());
//        model.addAttribute("user", testBranch);
//
//        //주문목록
//        Page<ProductOrder> list = null;
//
//        //본점과 지점이 보는 주문목록이 다름
//        if (testBranch.getAuthority().equals(Authority.ADMIN)) {
//            //전체 주문목록
//            list = testProductOrderService.orderList(pageable);
//        } else {
//            //지점 주문목록
//            Long branchId = testBranch.getBranchId();
//            list = testProductOrderService.orderListByBranch(pageable, branchId);
//        }
//
//        model.addAttribute("list", list);
//
//        //페이징변수 설정 - list가 null이 아닐 때만 페이징 변수 설정
//        if (list != null) {
//            int nowPage = list.getPageable().getPageNumber() + 1;
//            int startPage = Math.max(nowPage - 4, 1);
//            int endPage = Math.min(nowPage + 5, list.getTotalPages());
//            model.addAttribute("nowPage", nowPage);
//            model.addAttribute("startPage", startPage);
//            model.addAttribute("endPage", endPage);
//        }
//        model.addAttribute("startDate", startDate);
//        model.addAttribute("endDate", endDate);
//
//        if (testBranch.getAuthority().equals(Authority.ADMIN)) {
//            //전체 주문목록 (필터링 적용)
//            list = testProductOrderService.orderList(pageable, startDate, endDate);
//        } else {
//            //지점 주문목록 (필터링 적용)
//            Long branchId = testBranch.getBranchId();
//            list = testProductOrderService.orderListByBranch(pageable, branchId, startDate, endDate);
//        }
//        model.addAttribute("list", list);
//
//        return "test/orders";
//    }

    @GetMapping({"/test/orders", "/test/orders/{page}"})
    public String orderList(Principal principal, Model model,
                            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                            @PageableDefault(page = 0, size = 10, sort = "poDate", direction = Sort.Direction.DESC) Pageable pageable) {

        // 현재 로그인한 사용자의 지점 정보
        Branch branch = branchService.findBranchByEmail(principal.getName());
        model.addAttribute("user", branch);

        LocalDate now = LocalDate.now();
        if (startDate == null) {
            startDate = now.minus(1, ChronoUnit.MONTHS); // 한 달 전
        }
        if (endDate == null) {
            endDate = now; // 현재 날짜
        }

        // 주문 목록 필터링 적용
        Page<ProductOrder> list = null;
        if (branch.getAuthority().equals(Authority.ADMIN)) {
            // 전체 주문목록 (필터링 적용)
            list = productOrderService.orderList(pageable, startDate, endDate);
        } else {
            //지점 주문목록 (필터링 적용)
            Long branchId = branch.getBranchId();
            list = productOrderService.orderListByBranch(pageable, branchId, startDate, endDate);
        }

        model.addAttribute("list", list);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        
        // 페이징 변수 설정
        if (list != null) {
            int nowPage = list.getPageable().getPageNumber() + 1;
            int startPage = Math.max(nowPage - 4, 1);
            int endPage = Math.min(nowPage + 5, list.getTotalPages());
            model.addAttribute("nowPage", nowPage);
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);
        }

        return "test/orders";
    }
}
