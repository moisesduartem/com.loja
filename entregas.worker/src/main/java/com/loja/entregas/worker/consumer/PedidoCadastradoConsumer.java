package com.loja.entregas.worker.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class PedidoCadastradoConsumer {
    @RabbitListener(queues = { "pedido_cadastrado" })
    public void cadastrarEntrega(@Payload String fileBody) {
        System.out.println(fileBody);
    }
}
