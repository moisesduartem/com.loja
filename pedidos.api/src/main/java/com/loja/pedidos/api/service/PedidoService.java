package com.loja.pedidos.api.service;

import com.loja.pedidos.api.dto.CadastrarPedidoDto;
import com.loja.pedidos.api.entity.Cliente;
import com.loja.pedidos.api.entity.Pedido;
import com.loja.pedidos.api.entity.Produto;
import com.loja.pedidos.api.queue.QueueSender;
import com.loja.pedidos.api.repository.ClienteRepository;
import com.loja.pedidos.api.repository.PedidoRepository;
import com.loja.pedidos.api.repository.ProdutoRepository;
import com.loja.queue.events.PedidoCadastradoEvent;
import org.springframework.amqp.utils.SerializationUtils;
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

    public Pedido cadastrarPedido(CadastrarPedidoDto cadastroDto) {
        Cliente cliente = clienteRepository.getReferenceById(cadastroDto.getClienteId());
        List<Produto> produtos = produtoRepository.findAllById(cadastroDto.getProdutosIds());

        Pedido pedido = new Pedido(cliente, new HashSet<>(produtos));

        pedidoRepository.save(pedido);

        PedidoCadastradoEvent event = new PedidoCadastradoEvent(
                pedido.getId(),
                cadastroDto.getEndereco().trim().toUpperCase());

        byte[] data = SerializationUtils.serialize(event);

        queueSender.send("pedido_cadastrado", data);

        return pedido;
    }

}
