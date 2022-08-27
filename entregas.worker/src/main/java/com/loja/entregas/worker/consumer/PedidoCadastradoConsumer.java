package com.loja.entregas.worker.consumer;

import com.loja.queue.events.PedidoCadastradoEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class PedidoCadastradoConsumer {
    @RabbitListener(queues = { "pedido_cadastrado" })
    public void cadastrarEntrega(@Payload byte[] body) {
        try {
            PedidoCadastradoEvent data = (PedidoCadastradoEvent) SerializationUtils.deserialize(body);
            System.out.println(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
