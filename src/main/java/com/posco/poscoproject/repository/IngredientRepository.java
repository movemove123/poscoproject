package com.posco.poscoproject.repository;

import com.posco.poscoproject.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    @Query("SELECT ig FROM Ingredient ig WHERE ig.igId=:igId" )
    Ingredient findByIgId(@Param("igId")Long igId);
}
