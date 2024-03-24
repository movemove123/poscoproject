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
public class ProductOrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long podId;

    @Column
    private int podQuantity;

    @Column
    private int podSubtotal;

    @ManyToOne
    @JoinColumn(name="pd_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name="po_id")
    private ProductOrder productOrder;

    public ProductOrderDetail(Long podId, int podQuantity) {
        this.podId = podId;
        this.podQuantity = podQuantity;
    }

}
