package com.loja.pedidos.api.dto;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
public class CadastrarPedidoDto {
    @NotNull
    private Integer clienteId;

    @NotEmpty
    private Set<Integer> produtosIds;
}
