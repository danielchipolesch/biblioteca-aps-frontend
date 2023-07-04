package com.example.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.dto.ProdutoDTO;
import com.example.api.exceptions.RegraNegocioException;
import com.example.api.model.entity.Produto;
import com.example.api.service.ProdutoService;

@RestController
@RequestMapping("/api/produtos") // Quando for chamado no front, acessar via: http://localhost:8080/api/produtos
@CrossOrigin(origins = "*") 
public class ProdutoController {

	ProdutoService service;
	
	public ProdutoController(ProdutoService service) {
		this.service = service;
	}
	
	//método auxiliar para converter ObjetoDTO em uma Entidade
	private Produto converter(ProdutoDTO dto) {
		Produto produto = new Produto();
		
		produto.setNome(dto.getNome());
		produto.setQuantidade(dto.getQuantidade());
		produto.setValor(dto.getValor());
		
		if(dto.getId() != null) {
			produto.setId(dto.getId());
		}
		
		return produto;
	}
	
	@PostMapping("/salvar-produto")
	public ResponseEntity salvar(@RequestBody ProdutoDTO dto) { // http://localhost:8080/api/produtos/salvar-produto
		try {
			Produto entidadeProduto = converter(dto);
			entidadeProduto = service.salvar(entidadeProduto);
			return ResponseEntity.ok(entidadeProduto);
		} catch (RegraNegocioException regraNegocioException) {
			return ResponseEntity.badRequest().body(regraNegocioException.getMessage());
		}
	}
	
	@PutMapping("/atualizar-produto/{id}") // {id} indica que o parâmetro da url será variável
	public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ProdutoDTO dto) {
		return service.consultarPorId(id).map(entity -> {
			try {
				Produto produto = converter(dto);
				produto.setId(entity.getId());
				service.atualizar(produto);
				return ResponseEntity.ok(produto);
			} catch (RegraNegocioException regraNegocioException) {
				return ResponseEntity.badRequest().body(regraNegocioException.getMessage());
			}
		}).orElseGet( () ->  ResponseEntity.badRequest().body("O id do produto informado não foi encontrado na base de dados"));
	}
	
	@DeleteMapping("/deletar-produto/{id}") // {id} indica que o parâmetro da url será variável
	public ResponseEntity deletar(@PathVariable("id") Long id){
		return service.consultarPorId(id).map(entity -> {
			service.deletar(entity);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}).orElseGet(() ->  ResponseEntity.badRequest().body("O id do produto informado não foi encontrado na base de dados, por isso não pode ser excluído."));
	}
	
	@GetMapping("/buscar-produtos")
	public ResponseEntity buscar(
			@RequestParam(value = "nome", required = false) String nome,
			@RequestParam(value = "quantidade", required = false) Integer quantidade,
			@RequestParam(value = "valor", required = false) Long valor
			) {
		Produto produtoFiltro = new Produto();
		produtoFiltro.setNome(nome);
		produtoFiltro.setQuantidade(quantidade);
		produtoFiltro.setValor(valor);
		
		List<Produto> produtos = service.buscar(produtoFiltro);
		return ResponseEntity.ok(produtos);
	}
	
}
