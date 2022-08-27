package com.loja.shared.events;

import java.io.Serializable;

public class EnderecoEntregaEvent implements Serializable {
    private String numero;

    private String logradouro;

    private String cep;

    private String complemento;

    public EnderecoEntregaEvent(String numero, String logradouro, String cep, String complemento) {
        this.numero = numero;
        this.logradouro = logradouro;
        this.cep = cep;
        this.complemento = complemento;
    }

    public String getNumero() {
        return numero;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getCep() {
        return cep;
    }

    public String getComplemento() {
        return complemento;
    }
}
