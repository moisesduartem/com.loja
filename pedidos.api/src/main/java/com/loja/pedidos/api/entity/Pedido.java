package com.loja.pedidos.api.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity(name = "pedidos")
@Data
public class Pedido {
    @GeneratedValue
    @Id
    @Column(name = "cod_pedido")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "cod_cliente")
    private Cliente cliente;

    @ManyToMany
    @JoinTable(
        name = "pedidos_produtos",
        joinColumns = @JoinColumn(name = "cod_pedido", referencedColumnName = "cod_pedido"),
        inverseJoinColumns = @JoinColumn(name ="cod_produto", referencedColumnName = "cod_produto")
    )
    private List<Produto> produtos;
}
