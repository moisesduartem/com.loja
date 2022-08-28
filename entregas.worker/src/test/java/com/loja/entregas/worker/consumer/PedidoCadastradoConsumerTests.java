package com.loja.entregas.worker.consumer;

import com.loja.entregas.worker.entity.Entrega;
import com.loja.entregas.worker.factories.EnderecoFactory;
import com.loja.entregas.worker.repository.EntregaRepository;
import com.loja.entregas.worker.utils.JsonMapper;
import com.loja.shared.events.CadastrarEntregaEvent;
import com.loja.shared.events.EnderecoEntregaEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.amqp.utils.SerializationUtils;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PedidoCadastradoConsumerTests {

    @Mock
    private EntregaRepository entregaRepository;

    @Mock
    private EnderecoFactory enderecoFactory;

    @Mock
    private JsonMapper<Entrega> jsonMapper;

    @InjectMocks
    private PedidoCadastradoConsumer pedidoCadastradoConsumer;

    @Test
    public void quandoCadastrarUmaEntregaDeveCriarUmEnderecoAPartirDoEvento() {
        CadastrarEntregaEvent cadastrarEntregaEvent = new CadastrarEntregaEvent(
                1,
                new EnderecoEntregaEvent("1234", "av. abc def", "12345670", "compl 1")
        );

        pedidoCadastradoConsumer.cadastrarEntrega(SerializationUtils.serialize(cadastrarEntregaEvent));

        verify(enderecoFactory).createFromEvent(any(EnderecoEntregaEvent.class));
    }

    @Test
    public void quandoCadastrarUmaEntregaDeveSalvarNoBanco() {
        CadastrarEntregaEvent cadastrarEntregaEvent = new CadastrarEntregaEvent(
                1,
                new EnderecoEntregaEvent("1234", "av. abc def", "12345670", "compl 1")
        );

        pedidoCadastradoConsumer.cadastrarEntrega(SerializationUtils.serialize(cadastrarEntregaEvent));

        verify(entregaRepository).save(any(Entrega.class));
    }
}
