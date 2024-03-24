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
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pdId;

    @Column
    private String pdName;

    @Column
    private int pdPrice;

    @Column
    private String image;

    @Column
    private String pdCategory;

    @Column(name = "`desc`")
    private String desc; // 제품 설명

    private Boolean icedOnly; // 아이스 전용 여부

    private Double extraPrice; // 추가 가격

}
