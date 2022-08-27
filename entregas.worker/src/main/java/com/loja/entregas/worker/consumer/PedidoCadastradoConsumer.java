package com.loja.entregas.worker.consumer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loja.queue.events.PedidoCadastradoEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.boot.jackson.JsonObjectSerializer;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class PedidoCadastradoConsumer {
    @RabbitListener(queues = { "pedido_cadastrado" })
    public void cadastrarEntrega(@Payload byte[] body) {
        try {
            PedidoCadastradoEvent event = (PedidoCadastradoEvent) SerializationUtils.deserialize(body);
            ObjectMapper mapper = new ObjectMapper();
            mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            System.out.println(mapper.writeValueAsString(event));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
