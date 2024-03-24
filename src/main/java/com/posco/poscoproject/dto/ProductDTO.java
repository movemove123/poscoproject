package com.posco.poscoproject.dto;

import com.posco.poscoproject.entity.Ingredient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProductDTO {

    private Long pdId;

    private String pdName;

    private int pdPrice;

    private String pdCategory;

    private String pdDescript;

    private Ingredient ingredient;
}
