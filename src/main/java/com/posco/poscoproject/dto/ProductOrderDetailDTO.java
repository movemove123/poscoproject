package com.posco.poscoproject.dto;

import com.posco.poscoproject.entity.Product;
import com.posco.poscoproject.entity.ProductOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProductOrderDetailDTO {

    private Long podId;

    private int podQuantity;

    private int podSubtotal;

    private Product product;

    private ProductOrder productOrder;
}
