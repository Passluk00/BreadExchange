package it.uniromatre.breadexchange2_0;

import it.uniromatre.breadexchange2_0.auth.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AllArgsConstructor
class ApplicationTests {

    private final AuthService authService;

    /*

    @Test
    void testUserRegistration_withEmptyUsername() {

        RegistrationRequest req = RegistrationRequest.builder()

                .username("")
                .email("test@mail.com")
                .password("password")
                .build();

        assertThrows(ConstraintViolationException.class, () -> authService.register(req));

    }

     */

}
