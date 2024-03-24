package com.posco.poscoproject.repository;

import com.posco.poscoproject.entity.StockOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StockOrderRepository extends JpaRepository<StockOrder, Long> {

    @Query("SELECT so FROM StockOrder so WHERE so.branch.branchId=:branchId" )
    Page<StockOrder> findByBranchId(Pageable pageable, @Param("branchId")Long branchId);

    @Query("SELECT so FROM StockOrder so WHERE so.soId=:soId" )
    StockOrder findBySoId(@Param("soId")Long soId);
}
