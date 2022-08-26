package com.loja.pedidos.api.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity(name = "produtos")
@Data
public class Produto {
    @GeneratedValue
    @Id
    @Column(name = "cod_produto")
    private Integer id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "valor")
    private Double valor;

    @ManyToMany(mappedBy = "produtos")
    private List<Pedido> pedidos;
}
