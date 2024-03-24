package com.posco.poscoproject.dto;

import com.posco.poscoproject.entity.Ingredient;
import com.posco.poscoproject.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class RecipeDTO {

    private Long reId;

    private String reName;

    private int reQuantity;

    private Product product;

    private Ingredient ingredient;
}
