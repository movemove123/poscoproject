package com.posco.poscoproject.service;

import com.posco.poscoproject.enumtype.StockOrderStatus;
import com.posco.poscoproject.entity.*;
import com.posco.poscoproject.repository.StockOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class StockOrderService {

    private final StockOrderRepository stockOrderRepository;

    public Long createStockOrder(Ingredient ingredient, Branch branch, int soQuantity){
        StockOrder stockOrder = new StockOrder();
        stockOrder.setSoQuantity(soQuantity);
        stockOrder.setStockOrderStatus(StockOrderStatus.대기);
        stockOrder.setSoPayment(soQuantity*ingredient.getIgPrice());
        stockOrder.setSoCreatedDate(LocalDate.now());
        stockOrder.setIngredient(ingredient);
        stockOrder.setBranch(branch);
        stockOrderRepository.save(stockOrder);

        return stockOrder.getSoId();
    }

    public Page<StockOrder> stockOrderList(Pageable pageable) {
        return stockOrderRepository.findAll(pageable);
    }

    public Page<StockOrder> stockOrderListByBranch(Pageable pageable, Long branchId) {
        return stockOrderRepository.findByBranchId(pageable, branchId);
    }

    public void stockOrderDelete(Long soId){
        StockOrder stockOrder = stockOrderRepository.findById(soId).
                orElseThrow(EntityNotFoundException::new);
        stockOrderRepository.delete(stockOrder);
    }

    public StockOrder findBySoId(Long soId){
        return stockOrderRepository.findBySoId(soId);
    }

    public Long savePost(StockOrder stockOrder) {
        return stockOrderRepository.save(stockOrder).getSoId();
    }

    public StockOrder stockOrderConfirm(Long soId){
        StockOrder stockOrder = stockOrderRepository.findBySoId(soId);
        stockOrder.setStockOrderStatus(StockOrderStatus.승인);
        stockOrderRepository.save(stockOrder).getSoId();
        return stockOrder;
    }

    public Long stockOrderReject(Long soId){
        StockOrder stockOrder = stockOrderRepository.findBySoId(soId);
        stockOrder.setStockOrderStatus(StockOrderStatus.반려);
        return stockOrderRepository.save(stockOrder).getSoId();
    }
}
