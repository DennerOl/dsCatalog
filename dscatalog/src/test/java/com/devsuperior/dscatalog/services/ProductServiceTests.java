package com.devsuperior.dscatalog.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.service.ProductService;
import com.devsuperior.dscatalog.service.exceptions.DatabaseException;
import com.devsuperior.dscatalog.service.exceptions.ResourceNotFoundException;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {
	
	@InjectMocks
	private ProductService service;
	
	@Mock
	private ProductRepository repository;
	
	private long existingId;
	private long nonExistingId;
	private long dependetId;

// inicia antes de cada metodo	
	@BeforeEach
	void setUp() throws Exception{
		existingId = 1L;
		nonExistingId = 1000L;
		dependetId = 3L;
		
/* configuração do comportamento simulado do repository
 * quando chamar o deleteById com id existente ele não irá fazer nada
 */	
// simulo se o id é existente e retorno verdadeiro		
		Mockito.when(repository.existsById(existingId)).thenReturn(true);
		
		Mockito.when(repository.existsById(nonExistingId)).thenReturn(false);

// mokito id com integridade referencial no banco
		Mockito.when(repository.existsById(dependetId)).thenReturn(true);
		Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependetId);
		
		
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
