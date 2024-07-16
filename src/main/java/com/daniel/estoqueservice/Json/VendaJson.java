package com.daniel.estoqueservice.Json;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VendaJson {

    private String productId;
    private int quantity;
    private String paymentType;



}
