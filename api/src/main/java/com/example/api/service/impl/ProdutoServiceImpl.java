package com.example.api.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.api.model.entity.Produto;
import com.example.api.model.repository.ProdutoRepository;
import com.example.api.service.ProdutoService;

@Service
public class ProdutoServiceImpl implements ProdutoService {

	// Instanciar objeto repository para ter acesso aos métodos de pesistência
	private ProdutoRepository repository;
	
	// Contrutor
	public ProdutoServiceImpl(ProdutoRepository repo) {
		this.repository = repo;
	}
		
	@Override
	@Transactional
	public Produto salvar(Produto produtoParam) {
		return repository.save(produtoParam);
	}
	
	@Override
	@Transactional
	public Produto atualizar(Produto produtoParam) {
		Objects.requireNonNull(produtoParam.getId());
		return repository.save(produtoParam);
	}

	@Override
	@Transactional
	public void deletar(Produto produtoParam) {
		Objects.requireNonNull(produtoParam.getId());	
		repository.delete(produtoParam);
	}

	@Override
	@Transactional
	public List<Produto> buscar(Produto produtoParamFiltro) {
		Example example = Example.of(produtoParamFiltro);
		return repository.findAll(example);
	}

	@Override
	@Transactional
	public Optional<Produto> consultarPorId(Long id) {
		return repository.findById(id);
	}

}
