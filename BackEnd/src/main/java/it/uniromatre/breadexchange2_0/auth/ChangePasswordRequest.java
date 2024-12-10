package it.uniromatre.breadexchange2_0.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChangePasswordRequest {

    @NotEmpty(message = "Vecchia Password Obbligatoria")
    @NotBlank(message = "Vecchia Password Obbligatoria")
    private String currentPwd;

    @NotEmpty(message = "Nuova Password Obbligatoria")
    @NotBlank(message = "Nuova Password Obbligatoria")
    @Size(min = 8, message = "Passwod deve essere di Almeno 8 Caratteri")
    private String newPwd;

    @NotEmpty(message = "Le Password Devono Coincidere")
    @NotBlank(message = "Le Password Devono Coincidere")
    @Size(min = 8, message = "Passwod deve essere di Almeno 8 Caratteri")
    private String confirmNewPwd;

}
