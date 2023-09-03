package com.tcscontrol.control_backend;

import java.util.ArrayList;
import java.util.List;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;




@SpringBootApplication
public class ControlBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ControlBackendApplication.class, args);
	}

	// 	@Bean
	// CommandLineRunner initDatabase(UserNegocio userNegocio, PasswordEncoder passwordEncoder) {
	// 	return args -> {

	// 		userNegocio.deleteCascade(2121);

	// 		ContactsDTO contacts = new ContactsDTO(
	// 			null,
	// 			TypeContacts.EMAIL.getValue(),
	// 			"joseedston@teste.com"
	// 		);
	// 		ContactsDTO contacts2 = new ContactsDTO(
	// 			null,
	// 			TypeContacts.TELEFONE.getValue(),
	// 			"48 99999 9999"
	// 		);

	// 		List<ContactsDTO> contatos = new ArrayList<>();
	// 		contatos.add(contacts);
	// 		contatos.add(contacts2);

	// 		UserSenhaDTO user = new UserSenhaDTO(
	// 		    0L,
	// 			"teste",
	// 			2121,
	// 			passwordEncoder.encode("teste"),
	// 			"111.111.111-11",
	// 			null,
	// 			contatos,
	// 			Status.ACTIVE.getValue(),
	// 			TypeUser.ADMIN.getValue()
	// 		);
	// 		userNegocio.register(user);
	// 	};
	// }
}
