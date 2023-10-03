package com.devsuperior.dscatalog.dto;

import com.devsuperior.dscatalog.service.validation.UserInsertValid;

/* criado para carregar todos os daddos de um Use
 * incluso a senha. Sera usado quando for inserido um
 * novo usuario 
 */

@SuppressWarnings("serial")
@UserInsertValid
public class UserInsertDTO extends UserDTO{

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
