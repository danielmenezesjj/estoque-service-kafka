package com.daniel.estoqueservice.controller;


import com.daniel.estoqueservice.dto.ProductDTO;
import com.daniel.estoqueservice.service.EstoqueListener;
import com.daniel.estoqueservice.model.ProductEstoque;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/produtos")
public class ProductController {


    @Autowired
    EstoqueListener service;


    @GetMapping
    public ResponseEntity getProduct(){
        var products = service.list();
        return ResponseEntity.ok(products);
    }

    @PostMapping
    public ResponseEntity postProduct(@RequestBody ProductDTO dto){
        ProductEstoque productEstoque = new ProductEstoque(dto);
        service.create(productEstoque);
        return ResponseEntity.status(HttpStatus.CREATED).body(productEstoque);
    }

    @PutMapping("/{id}")
    public ResponseEntity putProduct(@PathVariable String id ,@RequestBody ProductDTO dto){
        service.update(id, dto);
        return ResponseEntity.ok().build();

    }


}
