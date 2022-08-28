package com.loja.pedidos.api.queue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class QueueSenderTests {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private QueueSender queueSender;

    @Test
    public void quandoEnviarAMensagemDeveChamarOMetodoConvertAndSend() {
        queueSender.send("nome_da_fila", new byte[] {});

        verify(rabbitTemplate).convertAndSend(any(String.class), any(byte[].class));
    }
}
