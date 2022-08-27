package com.loja.pedidos.api.service;

import com.loja.pedidos.api.dto.CadastrarPedidoDto;
import com.loja.pedidos.api.repository.ClienteRepository;
import com.loja.pedidos.api.repository.PedidoRepository;
import com.loja.pedidos.api.repository.ProdutoRepository;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PedidoServiceTests {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    public void when_cadastrar_pedido_should_get_clienteId_reference() {
//        CadastrarPedidoDto dto = new CadastrarPedidoDto();
//        dto.setClienteId(1);
//        dto.setEndereco("av. da universidade, 3264, 60020-181");
    }
}
