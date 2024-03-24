package com.posco.poscoproject.repository;

import com.posco.poscoproject.entity.Branch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Long> {

    // 로그인시, 페이지 이동시
    Optional<Branch> findByEmail(String email);

    // 모든 지점 목록을 조회하는 메서드
    List<Branch> findAll();

    @Query("SELECT br FROM Branch br" )
    Page<Branch> findAllBranch(Pageable pageable);


    // 당일 매출 합계 -  본사
    @Query("select sum(po.poPayment) from ProductOrder po where po.poDate =:date")
    Long getDailySalesAll(@Param("date") LocalDate date);

    // 전일 매출 합계 - 본사
    @Query("select sum(po.poPayment) from ProductOrder po where po.poDate = :date")
    Long getDailyLastSalesAll(@Param("date") LocalDate date);

    // 이번주 매출 합계 - 본사
    @Query(nativeQuery = true, value="SELECT SUM(po.po_payment) FROM product_order po WHERE po.po_date BETWEEN DATE_ADD(NOW(), INTERVAL -1 WEEK) AND NOW()")
    Long getWeekSalesAll();

    // 지난주 매출 합계 - 본사
    @Query(nativeQuery=true, value="SELECT SUM(po.po_payment) FROM product_order po WHERE po.po_date BETWEEN DATE_ADD(NOW(), INTERVAL -2 WEEK) AND DATE_ADD(NOW(), INTERVAL -1 WEEK)")
    Long getLastWeekSalesAll();

    // 이번달 매출 합계 - 본사
    @Query(nativeQuery = true, value="SELECT SUM(po.po_payment) FROM product_order po WHERE po.po_date BETWEEN DATE_ADD(NOW(), INTERVAL -1 MONTH) AND NOW()")
    Long getMonthSalesAll();

    // 지난달 매출 합계 - 본사
    @Query(nativeQuery = true, value="SELECT SUM(po.po_payment) FROM product_order po WHERE po.po_date BETWEEN DATE_ADD(NOW(), INTERVAL -2 MONTH) AND DATE_ADD(NOW(), INTERVAL -1 MONTH)")
    Long getLastMonthSalesAll();

    // 모든 지점 가져오기 - 본사
    @Query("select count(br.branchId) from Branch br")
    int getBranchCount();

    // 일별 매출 - 본사
    @Query(value = "SELECT DATE(po.po_date) as orderDate, SUM(po.po_payment) as totalSales " +
            "FROM product_order po " +
            "WHERE po.po_date BETWEEN :startDate AND :endDate " +
            "GROUP BY DATE(po.po_date) " +
            "ORDER BY DATE(po.po_date) ASC",
            nativeQuery = true)
    List<Object[]> findDailySalesForLast7Days(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    // 가장 많이 팔린 메뉴 top5
    @Query("SELECT p.pdName as itemName, SUM(pod.podQuantity) as totalSold " +
            "FROM ProductOrderDetail pod " +
            "JOIN pod.product p " +
            "GROUP BY p.pdName " +
            "ORDER BY totalSold DESC")
    List<Object[]> findTop5SoldItems(Pageable pageable);

    // 각 월별 매출액 합계 - 본사
    @Query(nativeQuery = true, value="SELECT MONTH(po.po_date) as month, SUM(po.po_payment) as totalSales " +
            "FROM product_order po " +
            "WHERE YEAR(po.po_date) = YEAR(CURRENT_DATE()) " + // 현재 년도의 데이터만 조회합니다.
            "GROUP BY MONTH(po.po_date) " + // 월별로 그룹화합니다.
            "ORDER BY month") // 월별로 정렬합니다.
    List<Object[]> getMonthlySales();

    // 지점 매출 순위 top10
    @Query("SELECT po.branch.branchId, po.branch.branchName, SUM(po.poPayment) " +
            "FROM ProductOrder po " +
            "WHERE po.poDate BETWEEN :startOfMonth AND :endOfMonth " +
            "GROUP BY po.branch.branchId, po.branch.branchName " +
            "ORDER BY SUM(po.poPayment) DESC")
    List<Object[]> findTop10SalesBranchesThisMonth(@Param("startOfMonth") LocalDate startOfMonth,
                                                   @Param("endOfMonth") LocalDate endOfMonth,
                                                   Pageable pageable);

    // 일별 매출 - 지점
    @Query(value = "SELECT DATE(po.po_date) AS orderDate, SUM(po.po_payment) AS totalSales " +
            "FROM product_order po " +
            "WHERE po.po_date BETWEEN :startDate AND :endDate " +
            "AND po.branch_id = :branchId " +
            "GROUP BY DATE(po.po_date) " +
            "ORDER BY DATE(po.po_date) ASC",
            nativeQuery = true)
    List<Object[]> findDailySalesForLast7DaysByBranch(@Param("startDate") LocalDate startDate,
                                                      @Param("endDate") LocalDate endDate,
                                                      @Param("branchId") Long branchId);

    // 가장 많이 팔린 메뉴 top5 - 지점
    @Query("SELECT p.pdName as itemName, SUM(pod.podQuantity) as totalSold " +
            "FROM ProductOrderDetail pod " +
            "JOIN pod.product p " +
            "JOIN pod.productOrder po " +
            "WHERE po.branch.branchId = :branchId " + // 특정 지점의 주문만 고려
            "GROUP BY p.pdName " +
            "ORDER BY totalSold DESC")
    List<Object[]> findTop5SoldItemsByBranch(@Param("branchId") Long branchId, Pageable pageable);

    // 각 월별 매출액 합계 - 지점
    @Query(nativeQuery = true, value="SELECT MONTH(po.po_date) as month, SUM(po.po_payment) as totalSales " +
            "FROM product_order po " +
            "WHERE YEAR(po.po_date) = YEAR(CURRENT_DATE()) " + // 현재 년도의 데이터만 조회합니다.
            "AND po.branch_id = :branchId " + // 본인 소속 지점의 데이터만 조회합니다.
            "GROUP BY MONTH(po.po_date) " + // 월별로 그룹화합니다.
            "ORDER BY month") // 월별로 정렬합니다.
    List<Object[]> getMonthlySalesByBranch(@Param("branchId") Long branchId);

    // 당일 매출 합계 -  지점
    @Query("select sum(po.poPayment) from ProductOrder po where po.poDate = :date and po.branch.branchId = :id")
    Long getDailySalesById(@Param("date") LocalDate date, @Param("id") Long id);

    // 이번주 매출 합계 - 지점
    @Query(nativeQuery = true, value="SELECT SUM(po.po_payment) FROM product_order po WHERE po.po_date BETWEEN DATE_ADD(NOW(), INTERVAL -1 WEEK ) AND NOW() AND po.branch_id = :id")
    Long getWeekSalesById(@Param("id") Long id);

    // 지난주 매출 합계 - 지점
    @Query(nativeQuery=true, value="SELECT SUM(po.po_payment) FROM product_order po WHERE po.po_date BETWEEN DATE_ADD(NOW(), INTERVAL -2 WEEK ) AND DATE_ADD(NOW(), INTERVAL -1 WEEK) AND po.branch_id = :id")
    Long getLastWeekSalesById(@Param("id") Long id);

    // 이번달 매출 합계 - 지점
    @Query(nativeQuery = true, value="SELECT SUM(po.po_payment) FROM product_order po WHERE po.po_date BETWEEN DATE_ADD(NOW(), INTERVAL -1 MONTH ) AND NOW() AND po.branch_id = :id")
    Long getMonthSalesById(@Param("id") Long id);

    // 지난달 매출 합계 - 지점
    @Query(nativeQuery = true, value="SELECT SUM(po.po_payment) FROM product_order po WHERE po.po_date BETWEEN DATE_ADD(NOW(), INTERVAL -2 MONTH ) AND DATE_ADD(NOW(), INTERVAL -1 MONTH) AND po.branch_id = :id")
    Long getLastMonthSalesById(@Param("id") Long id);

    // 일일 주문량 - 지점
    @Query(nativeQuery = true, value="SELECT COUNT(po.po_date) FROM product_order po WHERE DATE_FORMAT(po.po_date, '%Y-%m-%d') = DATE_FORMAT(NOW(), '%Y-%m-%d') AND po.branch_id = :id")
    int getOrderCountById(@Param("id") Long id);

    // 지점명으로 검색
    @Query("SELECT br FROM Branch br WHERE br.branchName LIKE %:branchName%")
    Page<Branch> findByBranchNameSearch(@Param("branchName") String branchName, Pageable pageable);

    // 지점 금일 주문량
    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM product_order WHERE DATE(po_date) = CURRENT_DATE AND branch_id = :branchId")
    int findTodaysOrderCountByBranchId(@Param("branchId") Long branchId);


}
