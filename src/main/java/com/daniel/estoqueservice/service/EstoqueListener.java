package com.daniel.estoqueservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.daniel.estoqueservice.Json.VendaJson;
import com.daniel.estoqueservice.dto.ProductDTO;
import com.daniel.estoqueservice.model.ProductEstoque;
import com.daniel.estoqueservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstoqueListener {


    @Autowired
    private ProductRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    public ProductEstoque create(ProductEstoque productEstoque) {
        return repository.save(productEstoque);
    }

    public List<ProductEstoque> list() {
        return repository.findAll();
    }

    public ProductEstoque update(String idProduto, ProductDTO dto) {
        Optional<ProductEstoque> optionalProductEstoque = repository.findByUUID(idProduto);
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
        try {
            VendaJson venda = objectMapper.readValue(mensagem, VendaJson.class);
            String productId = venda.getProductId();
            int quantity = venda.getQuantity();
            String paymentType = venda.getPaymentType();

            System.out.println("ProductId: " + productId);
            System.out.println("Quantity: " + quantity);
            System.out.println("PaymentType: " + paymentType);

            Optional<ProductEstoque> optionalProductEstoque = repository.findByUUID(productId);
            if (optionalProductEstoque.isPresent()) {
                ProductEstoque productEstoqueAtt = optionalProductEstoque.get();

                var quantidadeAtualizada = productEstoqueAtt.getQuantity() - quantity;
                productEstoqueAtt.setQuantity(quantidadeAtualizada); // Atualiza a quantidade corretamente
                ProductDTO productDTO = new ProductDTO(productEstoqueAtt.getProductName(), quantidadeAtualizada);
                productEstoqueAtt.updateEstoque(productDTO); // Atualiza o estoque com os dados do venda
                repository.save(productEstoqueAtt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

