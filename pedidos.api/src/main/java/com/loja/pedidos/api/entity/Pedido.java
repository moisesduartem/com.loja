package com.loja.pedidos.api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "pedidos")
@Data
@NoArgsConstructor
public class Pedido {
    @GeneratedValue
    @Id
    @Column(name = "cod_pedido")
    private Integer id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "cod_cliente")
    private Cliente cliente;

    @ManyToMany
    @JoinTable(
        name = "pedidos_produtos",
        joinColumns = @JoinColumn(name = "cod_pedido", referencedColumnName = "cod_pedido"),
        inverseJoinColumns = @JoinColumn(name ="cod_produto", referencedColumnName = "cod_produto")
    )
    private Set<Produto> produtos;

    public Pedido(Cliente cliente, Set<Produto> produtos) {
        this.cliente = cliente;
        this.produtos = produtos;
    }
}
