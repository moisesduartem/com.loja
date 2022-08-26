package com.loja.pedidos.api.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity(name = "clientes")
@Data
public class Cliente {
    @GeneratedValue
    @Id
    @Column(name = "cod_cliente")
    private Integer id;

    @Column(name = "nome")
    private String nome;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente")
    private List<Pedido> pedidos;
}
