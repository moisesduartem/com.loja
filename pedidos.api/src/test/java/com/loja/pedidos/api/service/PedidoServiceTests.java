package com.loja.pedidos.api.service;

import com.loja.pedidos.api.dto.CadastrarPedidoDto;
import com.loja.pedidos.api.dto.EnderecoEntregaDto;
import com.loja.pedidos.api.entity.Pedido;
import com.loja.pedidos.api.factories.EventFactory;
import com.loja.pedidos.api.queue.QueueSender;
import com.loja.pedidos.api.repository.ClienteRepository;
import com.loja.pedidos.api.repository.PedidoRepository;
import com.loja.pedidos.api.repository.ProdutoRepository;
import com.loja.pedidos.api.valueobjects.EnderecoEntrega;
import com.loja.shared.events.CadastrarEntregaEvent;
import com.loja.shared.events.EnderecoEntregaEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PedidoServiceTests {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private EventFactory pedidoCadastradoEventFactory;

    @Mock
    private QueueSender queueSender;

    @InjectMocks
    private PedidoService pedidoService;

    @Before
    public void setUp() {
        when(pedidoCadastradoEventFactory
                .createCadastrarEntregaEvent(any(Pedido.class), any(EnderecoEntrega.class)))
                .thenReturn(new CadastrarEntregaEvent(
                        1,
                        new EnderecoEntregaEvent("1234", "abc", "123456", null))
                );
    }

    @Test
    public void quandoCadastrarPedidoDeveBuscarReferenciaDoClienteId() {
        CadastrarPedidoDto dto = criarCadastrarPedidoDto(
                1,
                new EnderecoEntregaDto("1234", "R. Abcde Fgh", "12345-789", null),
                Arrays.asList(1, 2, 3, 4)
        );

        pedidoService.cadastrarPedido(dto);

        verify(clienteRepository).getReferenceById(dto.getClienteId());
    }

    @Test
    public void quandoCadastrarPedidoDeveSalvarOPedidoGerado() {
        CadastrarPedidoDto dto = criarCadastrarPedidoDto(
                9999,
                new EnderecoEntregaDto("1234", "R. Abcde Fgh", "12345-789", null),
                Arrays.asList(1, 2, 3, 4)
        );

        pedidoService.cadastrarPedido(dto);

        verify(pedidoRepository).save(any(Pedido.class));
    }

    @Test
    public void quandoCadastrarPedidoDeveBuscarTodosOsProdutosDoPedido() {
        List<Integer> produtosIds = Arrays.asList(1, 2, 3, 4);
        CadastrarPedidoDto dto = criarCadastrarPedidoDto(
                1,
                new EnderecoEntregaDto("1234", "R. Abcde Fgh", "12345-789", null),
                produtosIds
        );

        pedidoService.cadastrarPedido(dto);

        verify(produtoRepository).findAllById(new HashSet<>(produtosIds));
    }

    @Test
    public void quandoCadastrarPedidoDeveEnviarUmEventoParaAFilaCorreta() {
        EnderecoEntregaDto enderecoDto =
                new EnderecoEntregaDto("1234", "R. Abcde Fgh", "12345-789", null);
        List<Integer> produtosIds = Arrays.asList(1, 2, 3, 4);
        CadastrarPedidoDto dto = criarCadastrarPedidoDto(
                1,
                enderecoDto,
                produtosIds
        );

        when(pedidoCadastradoEventFactory.createCadastrarEntregaEvent(any(Pedido.class), any(EnderecoEntrega.class)))
                .thenReturn(new CadastrarEntregaEvent(123, new EnderecoEntregaEvent(enderecoDto.getNumero(),
                        enderecoDto.getLogradouro(),
                        enderecoDto.getCep(),
                        enderecoDto.getComplemento())));

        pedidoService.cadastrarPedido(dto);

        verify(queueSender).send(eq("cadastrar_entrega"), any());
    }

    @Test
    public void quandoCadastrarPedidoDeveRetornarOPedido() {
        EnderecoEntregaDto enderecoDto =
                new EnderecoEntregaDto("1234", "R. Abcde Fgh", "12345-789", null);
        List<Integer> produtosIds = Arrays.asList(1, 2, 3, 4);
        CadastrarPedidoDto dto = criarCadastrarPedidoDto(
                1,
                enderecoDto,
                produtosIds
        );

        Pedido pedido = pedidoService.cadastrarPedido(dto);

        assertThat(pedido).isNotNull();
    }

    @Test
    public void quandoCadastrarPedidoDeveGerarOEventoDeCadastrarEntrega() {
        EnderecoEntregaDto enderecoDto = new EnderecoEntregaDto("1234", "R. Abcde Fgh", "12345-789", null);
        List<Integer> produtosIds = Arrays.asList(1, 2, 3, 4);
        CadastrarPedidoDto cadastroPedidoDto = criarCadastrarPedidoDto(
                1,
                enderecoDto,
                produtosIds
        );

        pedidoService.cadastrarPedido(cadastroPedidoDto);

        ArgumentCaptor<Pedido> pedidoArgumentCaptor = ArgumentCaptor.forClass(Pedido.class);
        ArgumentCaptor<EnderecoEntrega> enderecoArgumentCaptor = ArgumentCaptor.forClass(EnderecoEntrega.class);

        verify(pedidoCadastradoEventFactory).createCadastrarEntregaEvent(
                pedidoArgumentCaptor.capture(),
                enderecoArgumentCaptor.capture()
        );

        Pedido pedidoArg = pedidoArgumentCaptor.getValue();
        EnderecoEntrega enderecoArg = enderecoArgumentCaptor.getValue();

        assertThat(pedidoArg).isNotNull();
        assertThat(enderecoArg).isNotNull();
        assertThat(enderecoArg.getNumero()).isEqualTo(enderecoDto.getNumero());
        assertThat(enderecoArg.getLogradouro()).isEqualTo(enderecoDto.getLogradouro());
        assertThat(enderecoArg.getCep()).isEqualTo(enderecoDto.getCep());
        assertThat(enderecoArg.getComplemento()).isEqualTo(enderecoDto.getComplemento());
    }

    private CadastrarPedidoDto criarCadastrarPedidoDto(Integer clienteId, EnderecoEntregaDto endereco, List<Integer> produtosIds) {
        return new CadastrarPedidoDto(
                clienteId,
                new HashSet(Arrays.asList(1, 2, 3, 4)),
                endereco
        );
    }
}
