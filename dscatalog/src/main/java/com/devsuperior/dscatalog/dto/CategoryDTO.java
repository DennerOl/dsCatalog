package com.devsuperior.dscatalog.dto;

import java.io.Serializable;

import org.springframework.beans.BeanUtils;

import com.devsuperior.dscatalog.entities.Category;

@SuppressWarnings("serial")
public class CategoryDTO implements Serializable {

	private Long id;
	private String  name;
	
	public CategoryDTO() {
		
	}
	
	public CategoryDTO(Category entity) {
		BeanUtils.copyProperties(entity, this);
	}

	public CategoryDTO(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
