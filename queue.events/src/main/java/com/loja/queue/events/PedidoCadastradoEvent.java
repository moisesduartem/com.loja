package com.loja.queue.events;

import java.io.Serializable;

public class PedidoCadastradoEvent implements Serializable {
    private Integer pedidoId;
    private String endereco;

    public PedidoCadastradoEvent(Integer pedidoId, String endereco) {
        this.pedidoId = pedidoId;
        this.endereco = endereco;
    }
}
