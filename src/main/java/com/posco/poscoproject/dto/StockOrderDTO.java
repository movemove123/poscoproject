package com.posco.poscoproject.dto;

import com.posco.poscoproject.entity.Branch;
import com.posco.poscoproject.entity.Ingredient;
import com.posco.poscoproject.enumtype.StockOrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class StockOrderDTO {

    private long soId;

    private int soQuantity;

    private StockOrderStatus stockOrderStatus;

    private int soPayment;

    private LocalDate soCreatedDate;

    private LocalDate soModifiedDate;

    private Ingredient ingredient;

    private Branch branch;
}
