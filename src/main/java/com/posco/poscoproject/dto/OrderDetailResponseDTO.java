package com.posco.poscoproject.dto;

import com.posco.poscoproject.entity.ProductOrder;
import com.posco.poscoproject.entity.ProductOrderDetail;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class OrderDetailResponseDTO {

    private Optional<ProductOrder> productOrder;
    private List<ProductOrderDetail> orderDetails;

}
