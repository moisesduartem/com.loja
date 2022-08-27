package com.loja.pedidos.api.valueobjects;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class EnderecoEntrega {

    private String numero;

    private String logradouro;

    private String cep;

    private String complemento;

    public EnderecoEntrega(String numero, String logradouro, String cep, String complemento) {
        this.numero = numero;
        this.logradouro = logradouro;
        this.cep = cep;
        this.complemento = complemento;
    }
}
