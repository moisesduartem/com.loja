package com.loja.pedidos.api.factories;

import com.loja.pedidos.api.entity.Cliente;
import com.loja.pedidos.api.entity.Pedido;
import com.loja.pedidos.api.entity.Produto;
import com.loja.pedidos.api.valueobjects.EnderecoEntrega;
import com.loja.shared.events.CadastrarEntregaEvent;
import com.loja.shared.events.EnderecoEntregaEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class EventFactoryTests {

    @InjectMocks
    private EventFactory eventFactory;

    @Test
    public void quandoCriarOEventoDeEnderecoDeveRetornarOsDadosCorretos() {
        EnderecoEntrega endereco = new EnderecoEntrega(
                "1234",
                "Av. Abc Def",
                "123456",
                "Compl 123"
        );

        EnderecoEntregaEvent event = eventFactory.createEnderecoEntregaEvent(endereco);

        assertThat(event.getNumero()).isEqualTo(endereco.getNumero());
        assertThat(event.getLogradouro()).isEqualTo(endereco.getLogradouro());
        assertThat(event.getCep()).isEqualTo(endereco.getCep());
        assertThat(event.getComplemento()).isEqualTo(endereco.getComplemento());
    }

    @Test
    public void quandoCriarOEventoDeCadastrarEntregaDeveRetornarOsDadosCorretos() {
        EnderecoEntrega endereco = new EnderecoEntrega(
                "1234",
                "Av. Abc Def",
                "123456",
                "Compl 123"
        );

        Cliente cliente = new Cliente();
        cliente.setId(1);
        cliente.setNome("Nome do Cliente");
        cliente.setPedidos(new ArrayList<>());

        Pedido pedido = new Pedido(cliente, new HashSet<Produto>());

        CadastrarEntregaEvent event = eventFactory.createCadastrarEntregaEvent(pedido, endereco);

        assertThat(event.getPedidoId()).isEqualTo(pedido.getId());
        assertThat(event.getEndereco().getNumero()).isEqualTo(endereco.getNumero());
        assertThat(event.getEndereco().getLogradouro()).isEqualTo(endereco.getLogradouro());
        assertThat(event.getEndereco().getCep()).isEqualTo(endereco.getCep());
        assertThat(event.getEndereco().getComplemento()).isEqualTo(endereco.getComplemento());
    }
}
