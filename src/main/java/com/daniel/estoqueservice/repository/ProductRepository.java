package com.daniel.estoqueservice.repository;

import com.daniel.estoqueservice.model.ProductEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEstoque, String> {
    @Query("SELECT e FROM Productestoque e WHERE e.id = :id")
    Optional<ProductEstoque> findByUUID(@Param("id") String id);
}
