package com.devsuperior.dscatalog.resources;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductResourceIntegration {
	
	@Autowired
	private MockMvc mockMvc;
	

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
	public void findAllDeveriaRetornarPageOrdenadaQndOrganizadaPorName() throws Exception {
		
		ResultActions result =
				mockMvc.perform(get("/products?page=0&size=12&sort=name,asc")
						.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
// testo se o valor do primeiro campo é igual countTotal
		result.andExpect(jsonPath("$.totalElements").value(countTotalProducts));
// testo se o obj content existe, ele traz os produtos da page
		result.andExpect(jsonPath("$.content").exists());
// testo se realmente está voltando ordenada a page
		result.andExpect(jsonPath("$.content[0].name").value("Macbook Pro"));
		result.andExpect(jsonPath("$.content[1].name").value("PC Gamer"));
		result.andExpect(jsonPath("$.content[2].name").value("PC Gamer Alfa"));
		
		
	}

}
