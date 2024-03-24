package com.posco.poscoproject.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private LocalDate poDate;

    @Column
    private int poPayment;

//    @OneToMany(mappedBy = "productOrder", fetch = FetchType.LAZY)
//    private List<ProductOrderDetail> productOrderDetails = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="branch_id")
    private Branch branch;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;
}

