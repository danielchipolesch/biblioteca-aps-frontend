package com.example.api.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api.model.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	
}
