package com.posco.poscoproject.controller;

import com.posco.poscoproject.dto.OrderDetailResponseDTO;
import com.posco.poscoproject.entity.ProductOrder;
import com.posco.poscoproject.entity.ProductOrderDetail;
import com.posco.poscoproject.repository.ProductOrderRepository;
import com.posco.poscoproject.service.ProductOrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@org.springframework.web.bind.annotation.RestController
@RequiredArgsConstructor
public class RestController {

    private final ProductOrderDetailService productOrderDetailService;
    private final ProductOrderRepository productOrderRepository;

//    @PostMapping("/orderDetail/{no}")
//    public @ResponseBody List<ProductOrderDetail> orderDetails(@PathVariable("no") Long poId) {
//        // findByProductOrderId로 List<ProductOrderDetail>을 받아온다
//        System.out.println("orderDetails>>>>>>>>>>>>>>>>>>>>>>>>>>>"+poId);
//        List<ProductOrderDetail> OrderDetailList = testProductOrderDetailService.getProductOrderDetail(poId);
//
//        return OrderDetailList;
//    }

    @PostMapping("/orderDetail/{no}")
    public @ResponseBody OrderDetailResponseDTO orderDetails(@PathVariable("no") Long poId) {
        List<ProductOrderDetail> orderDetailList = productOrderDetailService.getProductOrderDetail(poId);
        Optional<ProductOrder> productOrder = productOrderRepository.findById(poId); // ProductOrder 조회

        OrderDetailResponseDTO response = new OrderDetailResponseDTO();
        response.setOrderDetails(orderDetailList);
        response.setProductOrder(productOrder);

        return response;
    }
}
