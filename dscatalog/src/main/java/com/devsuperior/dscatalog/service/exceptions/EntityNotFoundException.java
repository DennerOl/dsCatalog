package com.devsuperior.dscatalog.service.exceptions;

@SuppressWarnings("serial")
public class EntityNotFoundException extends RuntimeException{

	public EntityNotFoundException(String msg) {
		super(msg);
	}
}
