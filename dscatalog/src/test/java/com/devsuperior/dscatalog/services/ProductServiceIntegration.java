package com.devsuperior.dscatalog.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.service.ProductService;
import com.devsuperior.dscatalog.service.exceptions.ResourceNotFoundException;

// carregando o contexto da aplicação
@SpringBootTest
public class ProductServiceIntegration {

	@Autowired
	private ProductService service;
	@Autowired
	private ProductRepository repository;

	private Long existingId;
	private Long nonExistingId;
	private Long countTotalProducts;

	// inicia antes de cada metodo
	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 1000L;
		countTotalProducts = 25L;

	}

	@Test
	public void deveriaDeletarRecursoQndIdExiste() {
		
		service.delete(existingId);
		
// testo se foi feito o delete
		Assertions.assertEquals(countTotalProducts - 1, repository.count());

	}

	@Test
	public void deveriaDeletaResourceNotFoundIdNoExiste() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, ()->{
			service.delete(nonExistingId);
		});
	}
	
}
