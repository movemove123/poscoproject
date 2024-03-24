package com.posco.poscoproject.service;

//import com.posco.poscoproject.entity.OrderBranch;
import com.posco.poscoproject.entity.ProductOrder;
import com.posco.poscoproject.repository.ProductOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductOrderService {

    private final ProductOrderRepository productOrderRepository;

    public Page<ProductOrder> orderList(Pageable pageable) {
        return productOrderRepository.findAll(pageable);
    }

    // 전체 주문목록 및 날짜 범위로 필터링된 목록 조회 (지점)
    public Page<ProductOrder> orderListByBranch(Pageable pageable, Long branchId, LocalDate startDate, LocalDate endDate) {
        return productOrderRepository.findByBranchIdAndDates(branchId, startDate, endDate, pageable);
    }

    // 전체 주문목록 및 날짜 범위로 필터링된 목록 조회 (지점 무관)
    public Page<ProductOrder> orderList(Pageable pageable, LocalDate startDate, LocalDate endDate) {
        return productOrderRepository.findByPoDateBetween(startDate, endDate, pageable);
    }


}
