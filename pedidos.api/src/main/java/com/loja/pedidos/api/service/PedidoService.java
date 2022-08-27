package com.loja.pedidos.api.service;

import com.loja.pedidos.api.dto.CadastrarPedidoDto;
import com.loja.pedidos.api.entity.Cliente;
import com.loja.pedidos.api.entity.Pedido;
import com.loja.pedidos.api.entity.Produto;
import com.loja.pedidos.api.factories.EventFactory;
import com.loja.pedidos.api.queue.QueueSender;
import com.loja.pedidos.api.repository.ClienteRepository;
import com.loja.pedidos.api.repository.PedidoRepository;
import com.loja.pedidos.api.repository.ProdutoRepository;
import com.loja.shared.events.CadastrarEntregaEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class PedidoService {

    private Logger logger = LoggerFactory.getLogger(PedidoService.class);

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private EventFactory pedidoCadastradoEventFactory;

    @Autowired
    private QueueSender queueSender;

    public Pedido cadastrarPedido(CadastrarPedidoDto cadastroDto) {
        logger.info("Buscando cliente com id={}", cadastroDto.getClienteId());
        Cliente cliente = clienteRepository.getReferenceById(cadastroDto.getClienteId());

        logger.info("Buscando produtos com ids={}", cadastroDto.getProdutosIds());
        List<Produto> produtos = produtoRepository.findAllById(cadastroDto.getProdutosIds());

        Pedido pedido = new Pedido(cliente, new HashSet<>(produtos));

        logger.info("Tentando salvar pedido no banco de dados");
        pedidoRepository.save(pedido);

        logger.info("Pedido cadastrado com sucesso, id={}", pedido.getId());
        CadastrarEntregaEvent event = pedidoCadastradoEventFactory.createCadastrarEntregaEvent(
                pedido,
                cadastroDto.getEndereco().toEntity()
        );

        logger.info(
                "Serializando evento com pedidoId={} e endereco={}",
                event.getPedidoId(),
                event.getEndereco()
        );
        byte[] data = SerializationUtils.serialize(event);

        logger.info("Enviando evento de pedidoId={} para fila", event.getPedidoId());
        queueSender.send("cadastrar_entrega", data);

        logger.info("Evento de criação do pedido enviado para fila com sucesso");
        logger.info("Retornando pedido criado");
        return pedido;
    }

}
