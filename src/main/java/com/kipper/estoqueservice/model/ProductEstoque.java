package com.kipper.estoqueservice.model;


import com.kipper.estoqueservice.dto.ProductDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "estoque-product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductEstoque {

    @GeneratedValue
    @Id
    private Integer id;
    private String productName;
    private Integer quantity;


    public ProductEstoque(ProductDTO dto){
        this.productName = dto.productName();
        this.quantity = dto.quantity();
    }

    public void updateEstoque(ProductDTO dto){
        if(dto.productName() != null){
            this.productName = dto.productName();
        }
        if(dto.quantity() != null){
            this.quantity = dto.quantity();
        }
    }

}
