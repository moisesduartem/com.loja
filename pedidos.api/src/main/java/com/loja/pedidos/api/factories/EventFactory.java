package com.loja.pedidos.api.factories;

import com.loja.pedidos.api.entity.Pedido;
import com.loja.pedidos.api.valueobjects.EnderecoEntrega;
import com.loja.shared.events.EnderecoEntregaEvent;
import com.loja.shared.events.CadastrarEntregaEvent;
import org.springframework.stereotype.Component;

@Component
public class EventFactory {
    public EnderecoEntregaEvent createEnderecoEntregaEvent(EnderecoEntrega endereco) {
        return new EnderecoEntregaEvent(endereco.getNumero(),
                endereco.getLogradouro(),
                endereco.getCep(),
                endereco.getComplemento()
        );
    }

    public CadastrarEntregaEvent createCadastrarEntregaEvent(Pedido pedido, EnderecoEntrega endereco) {
        EnderecoEntregaEvent enderecoEvent = createEnderecoEntregaEvent(endereco);

        return new CadastrarEntregaEvent(
            pedido.getId(),
            enderecoEvent
        );
    }
}
