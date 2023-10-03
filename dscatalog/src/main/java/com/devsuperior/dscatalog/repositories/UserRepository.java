package com.devsuperior.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsuperior.dscatalog.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
/* tenho que implementar um metodo para a classe
 * UserInsertValidator verificar se email existe 	
 */
	
	User findByEmail(String email);

}
