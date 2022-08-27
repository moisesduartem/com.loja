package com.loja.entregas.worker.entity;

import com.loja.entregas.worker.valueobjects.EnderecoEntrega;
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

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "numero", column = @Column(name = "numero_endereco")),
            @AttributeOverride(name = "logradouro", column = @Column(name = "logradouro")),
            @AttributeOverride(name = "cep", column = @Column(name = "cep")),
            @AttributeOverride(name = "complemento", column = @Column(name = "complemento")),
    })
    private EnderecoEntrega endereco;

    public Entrega(Integer pedidoId, EnderecoEntrega endereco) {
        this.pedidoId = pedidoId;
        this.endereco = endereco;
    }
}
