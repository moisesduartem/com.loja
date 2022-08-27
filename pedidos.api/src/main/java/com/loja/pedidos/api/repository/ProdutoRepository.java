package com.loja.pedidos.api.repository;

import com.loja.pedidos.api.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
}
