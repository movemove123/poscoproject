package com.posco.poscoproject.repository;


import com.posco.poscoproject.entity.ProductOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {

    @Query("SELECT po FROM ProductOrder po WHERE po.branch.branchId=:branchId" )
    Page<ProductOrder> findByBranchId(Pageable pageable, @Param("branchId")Long branchId);

    // 전체 주문목록 및 날짜 범위로 필터링 (지점)
    @Query("SELECT po FROM ProductOrder po WHERE po.branch.branchId= :branchId AND po.poDate BETWEEN :startDate AND :endDate")
    Page<ProductOrder> findByBranchIdAndDates(Long branchId, LocalDate startDate, LocalDate endDate, Pageable pageable);

    // 전체 주문목록 및 날짜 범위로 필터링 (지점 무관)
    @Query("SELECT po FROM ProductOrder po WHERE po.poDate BETWEEN :startDate AND :endDate")
    Page<ProductOrder> findByPoDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

}
