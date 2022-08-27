package com.loja.pedidos.api.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "clientes")
@Data
public class Cliente {
    @GeneratedValue
    @Id
    @Column(name = "cod_cliente")
    private Integer id;

    @Column(name = "nome")
    private String nome;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente")
    private List<Pedido> pedidos;
}
