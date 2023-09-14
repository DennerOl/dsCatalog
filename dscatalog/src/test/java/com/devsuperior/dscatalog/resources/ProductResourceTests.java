package com.devsuperior.dscatalog.resources;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.print.attribute.standard.Media;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.service.ProductService;
import com.devsuperior.dscatalog.service.exceptions.DatabaseException;
import com.devsuperior.dscatalog.service.exceptions.ResourceNotFoundException;
import com.devsuperior.dscatalog.testes.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;

// teste da camada controler anotação
@WebMvcTest(ProductResource.class)
public class ProductResourceTests {

// para chamar os endpoints	
	@Autowired
	private MockMvc mockMvc;
// usar esta anotação junto com WebMvc	
	@MockBean
	private ProductService service;
	
// converte o obj java para json
	@Autowired
	private ObjectMapper objectMapper;

	private long existingId;
	private long nonExistingId;
	private long dependentId;
	private ProductDTO productDTO;
	private PageImpl<ProductDTO> page;

	
	@BeforeEach
	void setUp() throws Exception{
		existingId = 1 ;
		nonExistingId = 2 ;
		dependentId = 3;
		
		
		productDTO = Factory.createProductDTO();
// instancio uma lista ja com elemento dentro dela
		page = new PageImpl<>(List.of(productDTO));
// chamo no service com qualquer argumento e retorno page		
		when(service.findAllPaged(any())).thenReturn(page);
		
// simulação service para findById
		when(service.findByid(existingId)).thenReturn(productDTO);
		when(service.findByid(nonExistingId)).thenThrow(ResourceNotFoundException.class);

// simulação service para update
		when(service.update(eq (existingId), any())).thenReturn(productDTO);
		when(service.update(eq (nonExistingId), any())).thenThrow(ResourceNotFoundException.class);

// simulação service para insert para qualquer valor retorna dto
		when(service.insert(any())).thenReturn(productDTO);
		
		
/* quando chamar o service com id existente não faça nada 
 * simulação service para delete VOID		
 */
	doNothing().when(service).delete(existingId);
	doThrow(ResourceNotFoundException.class).when(service).delete(nonExistingId);
	doThrow(DatabaseException.class).when(service).delete(dependentId);
	
	
	}
	
	@Test void deleteDeveDeletarQuandoIdExiste() throws Exception {
		
		ResultActions result =
				mockMvc.perform(delete("/products/{id}", existingId)
						.accept(MediaType.APPLICATION_JSON));
		
				result.andExpect(status().isNoContent());
	}
	
	@Test void deleteDeveRetornarDatabaseExceptionIntegridadeBD() throws Exception {
		
		ResultActions result =
				mockMvc.perform(delete("/products/{id}", dependentId)
						.accept(MediaType.APPLICATION_JSON));
		
				result.andExpect(status().isBadRequest());
		
	}
	
@Test void deleteDeveRetornarResourceNotFoundExceptionIdNoExistente() throws Exception {
		
		ResultActions result =
				mockMvc.perform(delete("/products/{id}", nonExistingId)
						.accept(MediaType.APPLICATION_JSON));
		
				result.andExpect(status().isNotFound());
		
	}
	
	@Test
	public void insertDeveriaRetornarProduct() throws Exception {
		// converto para string o dto
		String jsonBody = objectMapper.writeValueAsString(productDTO);
		
		ResultActions result =
				mockMvc.perform(post("/products")
						.content(jsonBody)
						// tipo do corpo da requisição é json
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isCreated());
		result.andExpect(jsonPath("$.id").exists());
		result.andExpect(jsonPath("$.name").exists());
		result.andExpect(jsonPath("$.description").exists());
		
				result.andExpect(status().isCreated());
	}
	
	@Test
	public void findAllDeveriaRetornarPageProducts() throws Exception{
		
		ResultActions result =
				mockMvc.perform(get("/products")
						.accept(MediaType.APPLICATION_JSON));
		
				result.andExpect(status().isOk());
	}
	
	@Test
	public void findByIdDeveriaRetornarProdutoQuandoIdExiste() throws Exception {
		ResultActions result =
				mockMvc.perform(get("/products/{id}", existingId)
						.accept(MediaType.APPLICATION_JSON));
		
				result.andExpect(status().isOk());
// testo se os atributos informados existe no obj json				
				result.andExpect(jsonPath("$.id").exists());
				result.andExpect(jsonPath("$.name").exists());
				result.andExpect(jsonPath("$.description").exists());
	}
	
	@Test
	public void findByIdDeveriaRetornarNotFoundQuandoIdInexiste() throws Exception {
		ResultActions result =
				mockMvc.perform(get("/products/{id}", nonExistingId)
						.accept(MediaType.APPLICATION_JSON));
		
				result.andExpect(status().isNotFound());
	}
	@Test
	public void updateDeveriaRetornarProdutoQuandoIdExiste() throws Exception {
		
		String jsonBody = objectMapper.writeValueAsString(productDTO);
			
		ResultActions result =
				mockMvc.perform(get("/products/{id}", existingId)
						.content(jsonBody)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.id").exists());
		result.andExpect(jsonPath("$.name").exists());
		result.andExpect(jsonPath("$.description").exists());
		
		}
		
		
	@Test
	public void updateDeveriaRetornarNotFoundQuandoIdInexiste() throws Exception {
	
		String jsonBody = objectMapper.writeValueAsString(productDTO);
		
		ResultActions result =
				mockMvc.perform(get("/products/{id}", nonExistingId)
						.content(jsonBody)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNotFound());
		
	}
}	
	
