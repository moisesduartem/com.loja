package com.loja.entregas.worker.consumer;

import com.loja.entregas.worker.entity.Entrega;
import com.loja.entregas.worker.repository.EntregaRepository;
import com.loja.entregas.worker.utils.JsonMapper;
import com.loja.queue.events.PedidoCadastradoEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class PedidoCadastradoConsumer {
    @Autowired
    private EntregaRepository entregaRepository;

    @Autowired
    private JsonMapper<Entrega> jsonMapper;

    @RabbitListener(queues = {"pedido_cadastrado"})
    public void cadastrarEntrega(@Payload byte[] body) {
        PedidoCadastradoEvent event = (PedidoCadastradoEvent) SerializationUtils.deserialize(body);

        Entrega entrega = new Entrega(event.getPedidoId(), event.getEndereco());

        entregaRepository.save(entrega);

        System.out.println(jsonMapper.mapToJsonString(entrega));
    }
}
