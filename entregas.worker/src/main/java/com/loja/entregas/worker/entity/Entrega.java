package com.loja.entregas.worker.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "entregas")
@Getter
public class Entrega {
    @GeneratedValue
    @Id
    @Column(name = "cod_entrega")
    private Integer id;

    @Column(name = "cod_pedido")
    private Integer pedidoId;

    @Column(name = "endereco_entrega")
    private String endereco;

    public Entrega(Integer pedidoId, String endereco) {
        this.pedidoId = pedidoId;
        this.endereco = endereco;
    }
}
