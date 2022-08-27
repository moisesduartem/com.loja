package com.loja.entregas.worker.consumer;

import com.loja.entregas.worker.entity.Entrega;
import com.loja.entregas.worker.repository.EntregaRepository;
import com.loja.entregas.worker.utils.JsonMapper;
import com.loja.queue.events.PedidoCadastradoEvent;
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
    private JsonMapper<Entrega> jsonMapper;

    @RabbitListener(queues = {"pedido_cadastrado"})
    public void cadastrarEntrega(@Payload byte[] body) {
        logger.info("Deserializando mensagem recebida");
        PedidoCadastradoEvent event = (PedidoCadastradoEvent) SerializationUtils.deserialize(body);

        logger.info(
                "Gerando entrega para pedidoId={} e endereco={}",
                event.getPedidoId(),
                event.getEndereco()
        );
        Entrega entrega = new Entrega(event.getPedidoId(), event.getEndereco());

        logger.info("Tentando salvar entrega no banco de dados");
        entregaRepository.save(entrega);

        logger.info("Entrega cadastrada com sucesso, id={}", entrega.getId());
        logger.info(jsonMapper.mapToJsonString(entrega));
    }
}
