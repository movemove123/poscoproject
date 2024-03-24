package com.posco.poscoproject.controller;

import com.posco.poscoproject.entity.Branch;
import com.posco.poscoproject.enumtype.Authority;
import com.posco.poscoproject.service.ChartService;
import com.posco.poscoproject.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class LoginController {
    @Autowired
    private ChartService chartService;
    private final BranchService branchService;

//    @GetMapping(value = {"/"})
//    public String income(){
//        System.out.println("login page");
//        return "test/testLogin";
//    }

    @GetMapping("/")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "exception", required = false) String exception,
                        Model model) {

        /* 에러와 예외를 모델에 담아 view resolve */
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "test/testLogin";

    }


    @GetMapping("/main")
    public String goMain(Model model, Principal principal){
        Branch branch = branchService.findBranchByEmail(principal.getName());
        model.addAttribute("user", branch);

        if(Authority.ADMIN.equals(branch.getAuthority())){

            long daily = branchService.dailySales().isPresent() ? branchService.dailySales().get() : 0 ;
            long week = branchService.weekSales();
            long month = branchService.monthSales();
            double compareLastWeek = branchService.compareLastWeek(week);
            double compareLastDaily = branchService.compareLastDaily(daily);

            model.addAttribute("count", branchService.getBranchCount());
            model.addAttribute("daily", daily);
            model.addAttribute("week", week);
            model.addAttribute("month", month);

            model.addAttribute("compareLstDaily", compareLastDaily);
            model.addAttribute("compareLstMonth", branchService.compareLastMonth(month));
            model.addAttribute("compareLstWeek", compareLastWeek);

            // 월별 매출 총액 그래프
            List<Object[]> monthlySalesData = chartService.getMonthlySales();
            model.addAttribute("monthlySalesData", monthlySalesData);
            // 가장 많이 팔린 메뉴5
            List<Object[]> topSoldItems = chartService.findTop5SoldItems();
            model.addAttribute("topSoldItems", topSoldItems);
            // 지점별 매출 상위 10개 지점 정보
            List<Object[]> topSalesBranches = chartService.findTop10SalesBranches();
            model.addAttribute("topSalesBranches", topSalesBranches);
        }else{

            long daily = branchService.dailySalesById(branch.getBranchId()).isPresent() ? branchService.dailySalesById(branch.getBranchId()).get() : 0 ;
            long week = branchService.weekSalesById(branch.getBranchId());
            long month = branchService.monthSalesById(branch.getBranchId());

            double compareLastDaily = branchService.compareLastDailyById(daily, branch.getBranchId());
            double compareLastWeek = branchService.compareLastWeekById(week, branch.getBranchId());
            int todaysOrderCount = branchService.getTodaysOrderCountByBranchId(branch.getBranchId()); // 금일 주문량 조회

            model.addAttribute("count", branchService.orderCountById(branch.getBranchId()));
            model.addAttribute("daily", daily);
            model.addAttribute("week", week);
            model.addAttribute("month", month);

            model.addAttribute("todaysOrderCount", todaysOrderCount); // 모델에 금일 주문량 추가

            model.addAttribute("compareLstDaily", compareLastDaily);
            model.addAttribute("compareLstMonth", branchService.compareLastMonthById(month, branch.getBranchId()));
            model.addAttribute("compareLstWeek", compareLastWeek);

            // 특정 지점의 월별 매출 데이터 추가
            List<Object[]> monthlySalesByBranch = chartService.getMonthlySalesByBranch(branch.getBranchId());
            model.addAttribute("monthlySalesDataByBranch", monthlySalesByBranch);

            // 특정 지점의 가장 많이 팔린 상품 상위 5개 추가
            List<Object[]> topSoldItemsByBranch = chartService.findTop5SoldItemsByBranch(branch.getBranchId());
            model.addAttribute("topSoldItemsByBranch", topSoldItemsByBranch);
        }
        return "main";
    }
}
