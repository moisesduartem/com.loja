package com.loja.entregas.worker.factories;

import com.loja.entregas.worker.valueobjects.EnderecoEntrega;
import com.loja.shared.events.EnderecoEntregaEvent;
import org.springframework.stereotype.Component;

@Component
public class EnderecoFactory {

    public EnderecoEntrega createFromEvent(EnderecoEntregaEvent event) {
        return new EnderecoEntrega(
                event.getNumero(),
                event.getLogradouro(),
                event.getCep(),
                event.getComplemento()
        );
    }
}
