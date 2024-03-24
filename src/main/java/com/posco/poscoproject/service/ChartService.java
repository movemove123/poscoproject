package com.posco.poscoproject.service;

import com.posco.poscoproject.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.time.LocalDate;

import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class ChartService {
    @Autowired
    private BranchRepository branchRepository;

    public Long getDailySalesAll(LocalDate date) {
        return branchRepository.getDailySalesAll(date);
    }

    public Long getWeekSalesAll() {
        return branchRepository.getWeekSalesAll();
    }

    public Long getLastWeekSalesAll() {
        return branchRepository.getLastWeekSalesAll();
    }

    public Long getMonthSalesAll() {
        return branchRepository.getMonthSalesAll();
    }

    public Long getLastMonthSalesAll() {
        return branchRepository.getLastMonthSalesAll();
    }

    public int getBranchCount() {
        return branchRepository.getBranchCount();
    }

    public List<Object[]> getMonthlySales() {
        return branchRepository.getMonthlySales();
    }

    // 일별 매출 - 본사
    public List<Object[]> findDailySalesForLast7Days(LocalDate startDate, LocalDate endDate) {
        return branchRepository.findDailySalesForLast7Days(startDate, endDate);
    }

    // 일별 매출 - 지점
    // 일별 매출 - 특정 지점
    public List<Object[]> findDailySalesForLast7DaysByBranch(LocalDate startDate, LocalDate endDate, Long branchId) {
        // Repository 메서드 호출
        return branchRepository.findDailySalesForLast7DaysByBranch(startDate, endDate, branchId);
    }

    // 지점 매출 순위 top10
    public List<Object[]> findTop10SalesBranches() {
        Pageable topTen = PageRequest.of(0, 10); // 상위 10개 지점
        LocalDate startOfMonth = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endOfMonth = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
        List<Object[]> topSalesBranchesThisMonth = branchRepository.findTop10SalesBranchesThisMonth(startOfMonth, endOfMonth, topTen);

        return topSalesBranchesThisMonth;
    }

    // 가장 많이 팔린 메뉴 top5
    public List<Object[]> findTop5SoldItems() { 
        Pageable topFive = PageRequest.of(0, 5); // 상위 5개 결과
        return branchRepository.findTop5SoldItems(topFive);
    }

    // 특정 지점의 월별 매출 데이터를 가져오는 메서드
    public List<Object[]> getMonthlySalesByBranch(Long branchId) {
        return branchRepository.getMonthlySalesByBranch(branchId);
    }

    // 지점별 데이터 가져오기
    public Long getDailySalesById(LocalDate date, Long id) {
        return branchRepository.getDailySalesById(date, id);
    }

    public Long getWeekSalesById(Long id) {
        return branchRepository.getWeekSalesById(id);
    }

    public Long getLastWeekSalesById(Long id) {
        return branchRepository.getLastWeekSalesById(id);
    }

    public Long getMonthSalesById(Long id) {
        return branchRepository.getMonthSalesById(id);
    }

    public Long getLastMonthSalesById(Long id) {
        return branchRepository.getLastMonthSalesById(id);
    }

    public int getOrderCountById(Long id) {
        return branchRepository.getOrderCountById(id);
    }

    // 가장 많이 팔린 메뉴 top5 - 지점
    public List<Object[]> findTop5SoldItemsByBranch(Long branchId) {
        Pageable topFive = PageRequest.of(0, 5); // 상위 5개 결과만 조회
        return branchRepository.findTop5SoldItemsByBranch(branchId, topFive);
    }
}
