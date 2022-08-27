package com.loja.pedidos.api.controller;

import com.loja.pedidos.api.dto.CadastrarPedidoDto;
import com.loja.pedidos.api.entity.Pedido;
import com.loja.pedidos.api.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("pedidos")
public class PedidosController {

    private PedidoService pedidoService;

    public PedidosController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<Pedido> cadastrarPedido(@RequestBody @Valid CadastrarPedidoDto dto) {
        Pedido pedido = pedidoService.cadastrarPedido(dto);
        return new ResponseEntity<>(pedido, HttpStatus.CREATED);
    }
}
