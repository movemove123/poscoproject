package com.posco.poscoproject.service;

import com.posco.poscoproject.entity.BranchInventory;
import com.posco.poscoproject.entity.StockOrder;
import com.posco.poscoproject.repository.BranchInventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BranchInventoryService {

    private final BranchInventoryRepository branchInventoryRepository;

    public List<BranchInventory> ingredientListByBranch(Long branchId){
        List<BranchInventory> branchInventories = branchInventoryRepository.findByBranch_BranchId(branchId);
        return branchInventories;
    }

    public int inventoryUpdate(StockOrder stockOrder){

        int soQuantity = stockOrder.getSoQuantity();
        Long igId = stockOrder.getIngredient().getIgId();
        Long branchId = stockOrder.getBranch().getBranchId();

        return branchInventoryRepository.inventoryUpdate(soQuantity,igId,branchId);
    }
}
