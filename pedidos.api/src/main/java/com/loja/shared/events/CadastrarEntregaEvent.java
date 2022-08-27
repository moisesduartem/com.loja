package com.loja.shared.events;

import java.io.Serializable;

public class CadastrarEntregaEvent implements Serializable {
    private Integer pedidoId;

    private EnderecoEntregaEvent endereco;

    public CadastrarEntregaEvent(Integer pedidoId, EnderecoEntregaEvent endereco) {
        this.pedidoId = pedidoId;
        this.endereco = endereco;
    }

    public Integer getPedidoId() {
        return pedidoId;
    }

    public EnderecoEntregaEvent getEndereco() {
        return endereco;
    }
}
