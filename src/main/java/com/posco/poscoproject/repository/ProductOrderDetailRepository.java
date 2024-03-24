package com.posco.poscoproject.repository;

import com.posco.poscoproject.entity.ProductOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductOrderDetailRepository extends JpaRepository<ProductOrderDetail, Long> {

    List<ProductOrderDetail> findByProductOrderId(@Param("po_id") Long poId);

}
