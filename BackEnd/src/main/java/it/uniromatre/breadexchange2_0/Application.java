package it.uniromatre.breadexchange2_0;

import it.uniromatre.breadexchange2_0.auth.AuthService;
import it.uniromatre.breadexchange2_0.auth.RegistrationRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableAsync
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(
            AuthService service
    ) {
        return args -> {

            // Admin user

            var admin = RegistrationRequest.builder()
                    .username("Admin")
                    .email("admin@mail.com")
                    .password("password")
                    .build();

            service.adminRegisterAndActive(admin);


        };
    }

}
