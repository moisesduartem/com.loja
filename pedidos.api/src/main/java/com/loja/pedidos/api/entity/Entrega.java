package com.loja.pedidos.api.entity;

import com.loja.pedidos.api.valueobjects.EnderecoEntrega;
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

    @OneToOne
    @JoinColumn(name = "cod_pedido")
    private Pedido pedido;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "numero", column = @Column(name = "numero_endereco")),
        @AttributeOverride(name = "logradouro", column = @Column(name = "logradouro")),
        @AttributeOverride(name = "cep", column = @Column(name = "cep")),
        @AttributeOverride(name = "complemento", column = @Column(name = "complemento")),
    })
    private EnderecoEntrega endereco;

    public Entrega(Pedido pedido, EnderecoEntrega endereco) {
        this.pedido = pedido;
        this.endereco = endereco;
    }
}
