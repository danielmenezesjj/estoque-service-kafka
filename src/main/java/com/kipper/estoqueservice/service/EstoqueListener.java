package com.kipper.estoqueservice.service;

import com.kipper.estoqueservice.dto.ProductDTO;
import com.kipper.estoqueservice.model.ProductEstoque;
import com.kipper.estoqueservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstoqueListener {


    @Autowired
    private ProductRepository repository;

    public ProductEstoque create(ProductEstoque productEstoque) {
        return repository.save(productEstoque);
    }

    public List<ProductEstoque> list() {
        return repository.findAll();
    }

    public ProductEstoque update(Integer idProduto, ProductDTO dto) {
        Optional<ProductEstoque> optionalProductEstoque = repository.findById(idProduto);
        if(optionalProductEstoque.isPresent()){
            ProductEstoque productEstoqueAtt = optionalProductEstoque.get();
            productEstoqueAtt.updateEstoque(dto);
            repository.save(productEstoqueAtt);
            return productEstoqueAtt;
        }
        throw new RuntimeException("Produto não encontrado!");
    }


    @KafkaListener(topics = "estoque-topico", groupId = "estoque-group")
    public void processarVenda(String mensagem) {
        System.out.println("Venda recebida: " + mensagem);
    }
}

