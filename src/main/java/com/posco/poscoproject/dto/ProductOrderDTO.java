package com.posco.poscoproject.dto;

import com.posco.poscoproject.entity.Branch;
import com.posco.poscoproject.entity.ProductOrderDetail;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProductOrderDTO {
    private long id;

    private LocalDate poDate;

    private int poPayment;

    private Branch branch;

    private List<ProductOrderDetail> productOrderDetails = new ArrayList<>();

}
