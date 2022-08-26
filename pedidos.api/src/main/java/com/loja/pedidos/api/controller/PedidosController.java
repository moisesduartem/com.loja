package com.loja.pedidos.api.controller;

import com.loja.pedidos.api.entity.Pedido;
import com.loja.pedidos.api.repository.PedidoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pedidos")
public class PedidosController {
    private PedidoRepository pedidoRepository;

    public PedidosController(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @PostMapping
    public ResponseEntity<Pedido> gravarPedido(@RequestBody Pedido pedido) {
        pedidoRepository.save(pedido);
        return new ResponseEntity<>(pedido, HttpStatus.CREATED);
    }
}
