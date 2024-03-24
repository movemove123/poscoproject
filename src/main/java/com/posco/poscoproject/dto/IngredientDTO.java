package com.posco.poscoproject.dto;

import com.posco.poscoproject.entity.Branch;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class IngredientDTO {

    private Long igId;

    private String igName;

    private int igQuantity;

    private int igPrice;

    private String igCategory;

    private Branch branch;
}
