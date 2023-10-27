package com.devsuperior.dscatalog.services;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.service.ProductService;
import com.devsuperior.dscatalog.service.exceptions.DatabaseException;
import com.devsuperior.dscatalog.service.exceptions.ResourceNotFoundException;
import com.devsuperior.dscatalog.testes.Factory;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {
	
	@InjectMocks
	private ProductService service;
	
	@Mock
	private ProductRepository repository;
	
	@Mock
	private CategoryRepository categoryRepository;
	
	private long existingId;
	private long nonExistingId;
	private long dependetId;	
	private PageImpl<Product> page;
	private Product product;
	private Category category;
	private ProductDTO productDTO;
	

// inicia antes de cada metodo	
	@BeforeEach
	void setUp() throws Exception{
		existingId = 1L;
		nonExistingId = 1000L;
		dependetId = 3L;
		product = Factory.createProduct();
		page = new PageImpl<>(List.of(product));
		category = Factory.createCategory();
		productDTO = Factory.createProductDTO();
		
/* configuração do comportamento simulado do repository
 * quando chamar o deleteById com id existente ele não irá fazer nada
 */	
// simulo se o id é existente e retorno verdadeiro		
		Mockito.when(repository.existsById(existingId)).thenReturn(true);
		
		Mockito.when(repository.existsById(nonExistingId)).thenReturn(false);

// mokito id com integridade referencial no banco
		Mockito.when(repository.existsById(dependetId)).thenReturn(true);
		Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependetId);
		
// mockito para findAll paginado 
	Mockito.when(repository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);	
	
/* mockito para Save do repository
 * quando eu chamar o Save em qualquer obj
 * retorna um produto
 */
	Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(product);
	
// mockito para findById
	Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(product));
	Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());
	
	Mockito.when(repository.getReferenceById(existingId)).thenReturn(product);
	Mockito.when(repository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);
	
	Mockito.when(categoryRepository.getReferenceById(existingId)).thenReturn(category);
	Mockito.when(categoryRepository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);
	}
	
	
	@Test
	public void updateRetornaProductDTO() {
		
		ProductDTO result = service.update(existingId, productDTO);
		
		Assertions.assertNotNull(result);
	}
	
	@Test
	public void updateRetornaNaoProductDTO() {
		Assertions.assertThrows(ResourceNotFoundException.class, ()->{
			 service.update(nonExistingId, productDTO);
		});
		
	}
	
	
	
	@Test
	public void findByIdRetornaProductDTOExisteId() {
		ProductDTO result = service.findByid(existingId);
		Assertions.assertNotNull(result);
	}
	
	@Test
	public void findByIdNaoRetornaProductDTONaoExisteId() {
		Assertions.assertThrows(ResourceNotFoundException.class, ()->{
			service.findByid(nonExistingId);
		});
	}
	
	
	@Test 
	public void findAllPageDeveRetornarUmaPagina() {
		
		Pageable pageable = PageRequest.of(0,10);
		Page<ProductDTO> result = service.findAllPaged1(pageable);
		
		Assertions.assertNotNull(result);
		Mockito.verify(repository).findAll(pageable);
		
	}
	
	
	@Test
	public void naoDeleteIdComIntegridadeNoBancoDados() {
		Assertions.assertThrows(DatabaseException.class, () -> {
			service.delete(dependetId);
		});
	}
	
	@Test
	public void deleteDeveriaResourceNotFoundExceptionIdNaoExistente() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, ()->{
			service.delete(nonExistingId);
		});
	}
	
	
	@Test
	public void deleteNaoFazNadaComIdExistente() {
		
// não deveria ter exceção na Assertions		
		Assertions.assertDoesNotThrow(() -> {
			service.delete(existingId);
		});
/* 
 * e verifico se a chamada ao metodo delete foi feita
 * pelo menos uma vez	
 */
		Mockito.verify(repository, Mockito.times(1)).deleteById(existingId);
		
	}

}
