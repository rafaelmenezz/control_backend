package com.tcscontrol.control_backend;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tcscontrol.control_backend.contacts.model.Contacts;
import com.tcscontrol.control_backend.enuns.Status;
import com.tcscontrol.control_backend.enuns.TypeContacts;
import com.tcscontrol.control_backend.enuns.TypeUser;
import com.tcscontrol.control_backend.file.StorageService;
import com.tcscontrol.control_backend.file.model.entity.StorageProperties;
import com.tcscontrol.control_backend.pessoa.user.model.UserRepository;
import com.tcscontrol.control_backend.pessoa.user.model.entity.User;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class ControlBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ControlBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}

	@Bean
	CommandLineRunner initDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {

			//userNegocio.deleteCascade("2121");

			User pesquisa = userRepository.findByNrMatricula("2121");
			if (pesquisa == null) {
			Contacts contacts = new Contacts();
			contacts.setDsContato("joseedston@teste.com");
			contacts.setTypeContacts(TypeContacts.EMAIL);
			Contacts contacts2 = new Contacts();
			contacts2.setDsContato("48 99999 9999");
			contacts2.setTypeContacts(TypeContacts.TELEFONE);


			User user = new User();
			user.setNmName("teste");
			user.setNrMatricula("2121");
			user.setNmSenha(new BCryptPasswordEncoder().encode("Administrador"));
			user.setNrCpf("111.111.111-11");
			user.setTpStatus(Status.ACTIVE);
			user.setTypeUser(TypeUser.ADMIN);
			user.setPrimeiroAcesso(false);
			contacts.setPessoa(user);
			contacts2.setPessoa(user);
			List<Contacts> contatos = new ArrayList<>();
			contatos.add(contacts);
			contatos.add(contacts2);


			user.getContacts().addAll(contatos);


			userRepository.save(user);
			}

		};
	}
}
