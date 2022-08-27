package com.loja.entregas.worker.consumer;

import com.loja.entregas.worker.entity.Entrega;
import com.loja.entregas.worker.factories.EnderecoFactory;
import com.loja.entregas.worker.repository.EntregaRepository;
import com.loja.entregas.worker.utils.JsonMapper;
import com.loja.entregas.worker.valueobjects.EnderecoEntrega;
import com.loja.queue.events.PedidoCadastradoEvent;
import com.loja.shared.events.CadastrarEntregaEvent;
import com.loja.shared.events.EnderecoEntregaEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class PedidoCadastradoConsumer {

    private Logger logger = LoggerFactory.getLogger(PedidoCadastradoConsumer.class);

    @Autowired
    private EntregaRepository entregaRepository;

    @Autowired
    private EnderecoFactory enderecoFactory;

    @Autowired
    private JsonMapper<Entrega> jsonMapper;

    @RabbitListener(queues = {"cadastrar_entrega"})
    public void cadastrarEntrega(@Payload byte[] body) {
        logger.info("Deserializando mensagem recebida");
        CadastrarEntregaEvent event = (CadastrarEntregaEvent) SerializationUtils.deserialize(body);

        logger.info("Gerando entrega para pedido com id={}", event.getPedidoId());
        EnderecoEntrega endereco = enderecoFactory.createFromEvent(event.getEndereco());
        Entrega entrega = new Entrega(event.getPedidoId(), endereco);

        logger.info("Tentando salvar entrega no banco de dados");
        entregaRepository.save(entrega);

        logger.info("Entrega cadastrada com sucesso, id={}", entrega.getId());
        logger.info(jsonMapper.mapToJsonString(entrega));
    }
}
