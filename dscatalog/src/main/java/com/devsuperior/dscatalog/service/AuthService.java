package com.devsuperior.dscatalog.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.EmailDTO;
import com.devsuperior.dscatalog.dto.NewPasswordDTO;
import com.devsuperior.dscatalog.entities.PasswordRecover;
import com.devsuperior.dscatalog.entities.User;
import com.devsuperior.dscatalog.repositories.PasswordRecoverRepository;
import com.devsuperior.dscatalog.repositories.UserRepository;
import com.devsuperior.dscatalog.service.exceptions.ResourceNotFoundException;

@Service
public class AuthService {

	@Value("${email.password-recover.token.minutes}")
	private Long tokenMinutes;
	
	@Value("${email.password-recover.uri}")
	private String  recoverUri;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private PasswordRecoverRepository passwordRecoverRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Transactional
	public void createRecoverToken(EmailDTO body) {
// busco o email do usuario no banco 
		User user = userRepository.findByEmail(body.getEmail());
		if (user == null) {
			throw new ResourceNotFoundException("Email não encontrado");
		}
		
// se não for null eu salvo no banco 	
		String token = UUID.randomUUID().toString();
		
		PasswordRecover entity = new PasswordRecover();
		entity.setEmail(body.getEmail());
		entity.setToken(token);
		entity.setExpiration(Instant.now().plusSeconds(tokenMinutes *60L));
		
		entity = passwordRecoverRepository.save(entity);
// envio o email para o user
		
		String text = "Acesse o link para definir uma nova senha\n\n"
				+ recoverUri + token + " Validade de " + tokenMinutes + "minutos";
		
		emailService.sendEmail(body.getEmail(), "Recuperação de senha", text);
		
		
	}
	
	@Transactional
	public void saveNewPassword(NewPasswordDTO body) {

// procuro um token que não está expirado 		
		List<PasswordRecover> result = passwordRecoverRepository.searchValidTokens(body.getToken(), Instant.now());
		if (result.size() == 0) {
			throw new ResourceNotFoundException("Token inválido");
		}
// pego o email do user 		
		User user = userRepository.findByEmail(result.get(0).getEmail());
// tenho que criptografar a senha do user		
		user.setPassword(passwordEncoder.encode(body.getPassword()));
		user = userRepository.save(user);
	}

}
