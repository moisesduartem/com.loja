package com.loja.pedidos.api.controller;

import com.loja.pedidos.api.dto.CadastrarPedidoDto;
import com.loja.pedidos.api.dto.EnderecoEntregaDto;
import com.loja.pedidos.api.entity.Cliente;
import com.loja.pedidos.api.entity.Pedido;
import com.loja.pedidos.api.entity.Produto;
import com.loja.pedidos.api.service.PedidoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.HashSet;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PedidosControllerTests {

    @Mock
    private PedidoService pedidoService;

    @InjectMocks
    private PedidosController pedidosController;

    @Test
    public void quandoCadastrarPedidoDeveChamarOMetodoCadastrarPedido() {
        CadastrarPedidoDto dto = new CadastrarPedidoDto(
                1,
                new HashSet<Integer>(Arrays.asList(1, 2, 3)),
                new EnderecoEntregaDto("1234", "logradouro", "123456789", "compl 1")
        );

        pedidosController.cadastrarPedido(dto);

        verify(pedidoService).cadastrarPedido(eq(dto));
    }

    @Test
    public void quandoCadastrarPedidoDeveRetornarARespostaCorreta() {
        CadastrarPedidoDto dto = new CadastrarPedidoDto(
                1,
                new HashSet<Integer>(Arrays.asList(1, 2, 3)),
                new EnderecoEntregaDto("1234", "logradouro", "123456789", "compl 1")
        );

        Cliente cliente = new Cliente();
        cliente.setId(1);
        cliente.setNome("Nome Do Cliente");

        Pedido pedidoEsperado = new Pedido(cliente, new HashSet<Produto>());

        when(pedidoService.cadastrarPedido(any(CadastrarPedidoDto.class)))
                .thenReturn(pedidoEsperado);

        ResponseEntity<Pedido> responseEntity = pedidosController.cadastrarPedido(dto);

        assertThat(responseEntity.getBody()).isEqualTo(pedidoEsperado);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
}
