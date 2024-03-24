package com.posco.poscoproject.service;

import com.posco.poscoproject.entity.Ingredient;
import com.posco.poscoproject.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public Ingredient findByIgId(Long igId){
        Ingredient ingredient = ingredientRepository.findByIgId(igId);
        return ingredient;
    }
}
