package com.loja.pedidos.api.dto;

import com.loja.pedidos.api.valueobjects.EnderecoEntrega;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
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
