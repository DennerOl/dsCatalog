package com.devsuperior.dscatalog.dto;

import com.devsuperior.dscatalog.service.validation.UserInsertValid;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/* criado para carregar todos os daddos de um Use
 * incluso a senha. Sera usado quando for inserido um
 * novo usuario 
 */

@SuppressWarnings("serial")
@UserInsertValid
public class UserInsertDTO extends UserDTO{

	@NotBlank(message = "Campo obrigatório")
	@Size(min = 8, message = "Deve ter no mínimo 8 caracteres")
	private String password;
	
	public UserInsertDTO() {
		super();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
