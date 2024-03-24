package com.posco.poscoproject.entity;

import com.posco.poscoproject.enumtype.StockOrderStatus;
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
public class StockOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long soId;

    @Column
    private int soQuantity;

    @Column
    @Enumerated(EnumType.STRING)
    private StockOrderStatus stockOrderStatus;

    @Column
    private int soPayment;

    @Column
    private LocalDate soCreatedDate;

    @Column
    private LocalDate soModifiedDate;

    @ManyToOne
    @JoinColumn(name="ig_id")
    private Ingredient ingredient;

    @ManyToOne
    @JoinColumn(name="branch_id")
    private Branch branch;
}
