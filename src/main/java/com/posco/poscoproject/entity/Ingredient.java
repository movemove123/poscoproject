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
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long igId;

    @Column
    private String igName;

    @Column
    private int igPrice;

    @Column
    private String igCategory;

    @ManyToOne
    @JoinColumn(name="branch_id")
    private Branch branch;

}
