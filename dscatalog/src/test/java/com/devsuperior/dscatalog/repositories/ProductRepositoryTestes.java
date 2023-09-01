package com.devsuperior.dscatalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.testes.Factory;

@DataJpaTest
public class ProductRepositoryTestes {
	
	@Autowired
	private ProductRepository repository;


// inicia antes de cada teste	
	private long  exintingId;
	private long countTotalProducts;
	
	@BeforeEach
	void setUp() throws Exception {
		
		 exintingId = 1L;
		 countTotalProducts = 25L;
	}
	
	@Test
	public void deveriaSalvarNovoIdQuandoForNullo() {
		Product product = Factory.createProduct();
		product.setId(null);
		
		product = repository.save(product);
		
// testo se id do produto salvo não é nulo		
		Assertions.assertNotNull(product.getId());
// testo se realmente o id é o próximo a ser criado no banco	
		Assertions.assertEquals(countTotalProducts + 1, product.getId());
		
		
	}
	
	@Test
	public void deveriaDeletarObjetoIdExiste() {
		
		repository.deleteById(exintingId);
		
		Optional<Product> result = repository.findById(exintingId);
		Assertions.assertFalse(result.isPresent());
	}
	
	@Test 
	public void retornaNaoVazioIdExistente() {
		Optional<Product> result = repository.findById(exintingId);
		Assertions.assertTrue(result.isPresent());
	}
	
	@Test
	public void retornaVazioIdNaoExistente() {
		Product product = Factory.createProduct();
		product.setId(null);
		Assertions.assertNull(product.getId());
		
	}
	
}
