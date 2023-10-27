package com.devsuperior.dscatalog.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.service.ProductService;
import com.devsuperior.dscatalog.service.exceptions.ResourceNotFoundException;

// carregando o contexto da aplicação
@SpringBootTest
@Transactional
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
	
	@Test
	public void findAllPagedDeveriaRetornarPage0Size10() {

// crio um obj de pagina (preparo os dados)
		PageRequest pageRequest = PageRequest.of(0, 10);
// faço a ação	
		Page<ProductDTO> result = service.findAllPaged1(pageRequest);
// Assertion para test
		
		Assertions.assertFalse(result.isEmpty());
// testo se realmente é a page 0
		Assertions.assertEquals(0, result.getNumber());
//testo se realmente a page contém 10 elementos
		Assertions.assertEquals(10, result.getSize());
//	testo se o total de produtos é igual a countTotalProd
		Assertions.assertEquals(countTotalProducts, result.getTotalElements());
		
		
		
	}
	
	@Test
	public void findAllPagedDeveriaRetornarVazioQndoPageNoExistir() {

// crio um obj de pagina (preparo os dados)
		PageRequest pageRequest = PageRequest.of(50, 10);
// faço a ação	
		Page<ProductDTO> result = service.findAllPaged1(pageRequest);
// Assertion para test
		
		Assertions.assertTrue(result.isEmpty());

		
	}
	
	@Test
	public void findAllPagedDeveriaRetornarPageEmOrdemPorName() {

// crio um obj de pagina (preparo os dados)
		PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("name"));
// faço a ação	
		Page<ProductDTO> result = service.findAllPaged1(pageRequest);
// Assertion para test
		
		Assertions.assertFalse(result.isEmpty());
// testo se o nome do primeiro elemento é igual ao informado
		Assertions.assertEquals("Macbook Pro", result.getContent().get(0).getName());
		Assertions.assertEquals("PC Gamer", result.getContent().get(1).getName());
		Assertions.assertEquals("PC Gamer Alfa", result.getContent().get(2).getName());
	}
}
