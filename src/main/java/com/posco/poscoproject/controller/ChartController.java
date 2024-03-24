package com.posco.poscoproject.controller;

import com.posco.poscoproject.entity.Branch;
import com.posco.poscoproject.service.BranchService;
//import com.posco.poscoproject.service.SampleService;

import com.posco.poscoproject.service.ChartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChartController {

    @Autowired
    private ChartService chartService;
    private final BranchService branchService;
//    private final BranchService branchService;

    @GetMapping("/chart/month")
    public String showChart(Model model) {
        // 월별 매출 총액 그래프
        List<Object[]> monthlySalesData = chartService.getMonthlySales();
        model.addAttribute("monthlySalesData", monthlySalesData);

        return "/chart/month"; // 차트를 표시할 View의 이름
    }

    // 지점별 차트 페이지를 보여주는 메서드
    @GetMapping("/chart/month/{branchId}")
    public String showBranchChart(@PathVariable("branchId") Long branchId, Model model, Principal principal) {
        Branch branch = branchService.findBranchByEmail(principal.getName());
        // 특정 지점의 월별 매출 데이터 추가
        List<Object[]> monthlySalesByBranch = chartService.getMonthlySalesByBranch(branch.getBranchId());
        model.addAttribute("monthlySalesDataByBranch", monthlySalesByBranch);

        return "/chart/month"; // 지점별 차트를 표시할 View의 이름
    }

    @GetMapping("/chart/daily")
    public String dailyAll(Model model, Principal principal){
        LocalDate startDate = LocalDate.now().minusDays(7); // 7일 전 날짜
        LocalDate endDate = LocalDate.now(); // 현재 날짜
        // ChartService를 통해 지난 7일간의 일별 매출 데이터를 조회
        List<Object[]> dailySalesData = chartService.findDailySalesForLast7Days(startDate, endDate);
        // 조회된 데이터를 모델에 추가
        model.addAttribute("dailySalesData", dailySalesData);
        model.addAttribute("user", branchService.findBranchByEmail(principal.getName()));
        return "/chart/daily";
    }

    @GetMapping("/chart/daily/{branchId}")
    public String dailySalesByBranch(@PathVariable("branchId") Long branchId, Model model, Principal principal) {
        LocalDate startDate = LocalDate.now().minusDays(7); // 7일 전 날짜
        LocalDate endDate = LocalDate.now(); // 현재 날짜
        // ChartService를 통해 특정 지점(branchId)의 지난 7일간의 일별 매출 데이터를 조회
        List<Object[]> dailySalesDataByBranch = chartService.findDailySalesForLast7DaysByBranch(startDate, endDate, branchId);

        // 조회된 데이터를 모델에 추가
        model.addAttribute("dailySalesDataByBranch", dailySalesDataByBranch);
        // 현재 사용자의 지점 정보를 모델에 추가 (예: 네비게이션 바에서 사용자의 지점명 표시 등에 사용)
        Branch currentBranch = branchService.findBranchByEmail(principal.getName());
        model.addAttribute("currentBranch", currentBranch);
        model.addAttribute("user", branchService.findBranchByEmail(principal.getName()));
        return "/chart/daily";
    }
}
