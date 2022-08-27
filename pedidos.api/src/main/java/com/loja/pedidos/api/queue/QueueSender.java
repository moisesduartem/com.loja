package com.loja.pedidos.api.queue;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueueSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(String queueName, byte[] data) {
        Queue queue = new Queue(queueName, true);
        rabbitTemplate.convertAndSend(queue.getName(), data);
    }
}
