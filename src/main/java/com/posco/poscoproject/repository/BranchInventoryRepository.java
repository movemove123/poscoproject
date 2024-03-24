package com.posco.poscoproject.repository;

import com.posco.poscoproject.entity.BranchInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface BranchInventoryRepository extends JpaRepository<BranchInventory, Long> {

    List<BranchInventory> findByBranch_BranchId(Long branchId);

    @Modifying
    @Transactional
    @Query("UPDATE BranchInventory bi SET bi.igQuantity = bi.igQuantity + :soQuantity WHERE bi.ingredient.igId = :igId AND bi.branch.branchId = :branchId")
    int inventoryUpdate(@Param("soQuantity") int soQuantity, @Param("igId") Long igId, @Param("branchId") Long branchId);
}
