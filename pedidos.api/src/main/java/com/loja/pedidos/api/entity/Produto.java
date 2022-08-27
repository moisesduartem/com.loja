package com.loja.pedidos.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "produtos")
@Getter
public class Produto {
    @GeneratedValue
    @Id
    @Column(name = "cod_produto")
    private Integer id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "valor")
    private Double valor;

    @JsonIgnore
    @ManyToMany(mappedBy = "produtos")
    private Set<Pedido> pedidos;
}
