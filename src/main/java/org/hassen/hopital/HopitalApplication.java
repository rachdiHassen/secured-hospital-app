package org.hassen.hopital;

import org.hassen.hopital.entities.Patient;
import org.hassen.hopital.repositories.PatientRepository;
import org.hassen.hopital.security.service.SecurityService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class HopitalApplication {

	public static void main(String[] args) {
		SpringApplication.run(HopitalApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner saveUsers (SecurityService securityService){
		return args ->{
			securityService.addNewUser("hassen","123","123");
			securityService.addNewUser("habiba","456","456");
			securityService.addNewUser("joud","789","789");

			securityService.addNewRoles("admin","");
			securityService.addNewRoles("user","");

			securityService.addRoleToUser("hassen","admin");
			securityService.addRoleToUser("hassen","user");
			securityService.addRoleToUser("habiba","user");
			securityService.addRoleToUser("joud","user");
		};
	}
	@Bean
	CommandLineRunner start(PatientRepository patientRepository){
		return args -> {
			List.of("mourad","abir","alia","saber","imen","dorra").forEach(name->{
				for (int i = 0; i < 3; i++) {
					Patient patient=new Patient();
					patient.setNom(name);
					patient.setDateNaissance(new Date(1987/10/12));
					patient.setMalade(Math.random() > 0.5);
					patient.setScore((int)(100+Math.random()*100));

					patientRepository.save(patient);
				}
			});
		};
	}
}
