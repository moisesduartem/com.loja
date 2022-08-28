package com.loja.pedidos.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CadastrarPedidoDto {
    @NotNull
    private Integer clienteId;

    @NotEmpty
    private Set<Integer> produtosIds;

    @NotNull
    private EnderecoEntregaDto endereco;
}
