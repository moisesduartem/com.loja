package com.loja.pedidos.api.dto;

import com.loja.pedidos.api.valueobjects.EnderecoEntrega;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EnderecoEntregaDto {

    @NotNull
    private String numero;

    @NotBlank
    private String logradouro;

    @NotBlank
    private String cep;

    private String complemento;

    public EnderecoEntrega toEntity() {
        return new EnderecoEntrega(numero, logradouro, cep, complemento);
    }
}
