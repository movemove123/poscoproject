package com.posco.poscoproject.service;

//import com.posco.poscoproject.dto.OrderDetailWithItemDTO;
import com.posco.poscoproject.entity.ProductOrderDetail;
import com.posco.poscoproject.repository.ProductOrderDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductOrderDetailService {

    private final ProductOrderDetailRepository productOrderDetailRepository;

    @Transactional
    public List<ProductOrderDetail> getProductOrderDetail(Long poId) {
        List<ProductOrderDetail> OrderDetails = productOrderDetailRepository.findByProductOrderId(poId);

        return OrderDetails;
    }
}
