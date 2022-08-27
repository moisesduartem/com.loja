package com.loja.pedidos.api.entity;

import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "entregas")
@Getter
public class Entrega {
    @GeneratedValue
    @Id
    @Column(name = "cod_entrega")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "cod_pedido")
    private Pedido pedido;

    @Column(name = "endereco_entrega")
    private String endereco;

    public Entrega(Pedido pedido, String endereco) {
        this.pedido = pedido;
        this.endereco = endereco;
    }
}
