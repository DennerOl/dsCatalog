package com.devsuperior.dscatalog.service.validation;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.devsuperior.dscatalog.dto.UserInsertDTO;
import com.devsuperior.dscatalog.entities.User;
import com.devsuperior.dscatalog.repositories.UserRepository;
import com.devsuperior.dscatalog.resources.exceptions.FieldMessage;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


/* Está classe implementa a logica da minha
 * validação customizada que verifica la no banco 
 * se o email que estou digitando ja existe
 */
public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserInsertDTO> {

@Autowired
private UserRepository repository;	
	
@Override
public void initialize(UserInsertValid ann) {
}

@Override
public boolean isValid(UserInsertDTO dto, ConstraintValidatorContext context) {
	
		List<FieldMessage> list = new ArrayList<>();
		
/*Coloque aqui seus testes de validação, acrescentando objetos FieldMessage à lista
 * testo se o email ja existe
 */
		User user = repository.findByEmail(dto.getEmail());
		
		if (user != null) {
			list.add(new FieldMessage("email", "Email já existe"));
		}
		
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}