package com.example.api.service;

import java.util.List;
import java.util.Optional;

import com.example.api.model.entity.Produto;

public interface ProdutoService {
	
	// CRUD
	
	Produto salvar(Produto produtoParam);
	
	Produto atualizar(Produto produtoParam);
	
	void deletar(Produto produtoParam);
	
	List<Produto> buscar(Produto produto);
	
	Optional<Produto> consultarPorId(Long id);

}
