package it.uniromatre.breadexchange2_0.auth;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegistrationRequest {


    @NotEmpty(message = "Username è Obbligatorio")
    @NotBlank(message = "Username è Obbligatorio")
    private String username;

    @Email(message = "Email formattata male")
    @NotEmpty(message = "Email è Obbligatoria")
    @NotBlank(message = "Email è Obbligatoria")
    private String email;

    @NotEmpty(message = "Password è Obbligatoria")
    @NotBlank(message = "Password è Obbligatoria")
    @Size(min = 8, message = "Passwod deve essere di Almeno 8 Caratteri")
    private String password;


}
