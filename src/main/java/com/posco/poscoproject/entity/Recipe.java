package com.posco.poscoproject.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reId;

    @Column
    private String reName;

    @Column
    private int reQuantity;

    @ManyToOne
    @JoinColumn(name="pd_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name="ig_id")
    private Ingredient ingredient;
}
