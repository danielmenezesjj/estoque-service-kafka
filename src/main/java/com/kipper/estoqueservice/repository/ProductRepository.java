package com.kipper.estoqueservice.repository;

import com.kipper.estoqueservice.model.ProductEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<ProductEstoque, Integer> {

}
