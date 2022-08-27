package com.loja.pedidos.api.service;

import com.loja.pedidos.api.dto.CadastrarPedidoDto;
import com.loja.pedidos.api.entity.Cliente;
import com.loja.pedidos.api.entity.Pedido;
import com.loja.pedidos.api.entity.Produto;
import com.loja.pedidos.api.repository.ClienteRepository;
import com.loja.pedidos.api.repository.PedidoRepository;
import com.loja.pedidos.api.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PedidoService {

    private PedidoRepository pedidoRepository;
    private ClienteRepository clienteRepository;
    private ProdutoRepository produtoRepository;

    public PedidoService(PedidoRepository pedidoRepository, ClienteRepository clienteRepository, ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
    }

    public Pedido cadastrarPedido(CadastrarPedidoDto dto) {
        Cliente cliente = clienteRepository.getReferenceById(dto.getClienteId());
        List<Produto> produtos = produtoRepository.findAllById(dto.getProdutosIds());

        Pedido pedido = new Pedido(cliente, new HashSet<>(produtos));

        pedidoRepository.save(pedido);

        return pedido;
    }

}
