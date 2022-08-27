package com.loja.pedidos.api.service;

import com.loja.pedidos.api.dto.CadastrarPedidoDto;
import com.loja.pedidos.api.entity.Cliente;
import com.loja.pedidos.api.entity.Pedido;
import com.loja.pedidos.api.entity.Produto;
import com.loja.pedidos.api.queue.QueueSender;
import com.loja.pedidos.api.repository.ClienteRepository;
import com.loja.pedidos.api.repository.PedidoRepository;
import com.loja.pedidos.api.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private QueueSender queueSender;

    public Pedido cadastrarPedido(CadastrarPedidoDto dto) {
        Cliente cliente = clienteRepository.getReferenceById(dto.getClienteId());
        List<Produto> produtos = produtoRepository.findAllById(dto.getProdutosIds());

        Pedido pedido = new Pedido(cliente, new HashSet<>(produtos));

        pedidoRepository.save(pedido);

        queueSender.send("pedido_cadastrado", "avenida x, rua y, 1234");

        return pedido;
    }

}
