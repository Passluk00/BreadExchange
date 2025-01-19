package it.uniromatre.breadexchange2_0.email;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class emailVerifyRequest {

    @NotNull(message = "L'email é Obbligatoria")
    @NotEmpty(message = "L'email é Obbligatoria")
    public String email;


    @NotNull(message = "L'email é Obbligatoria")
    @NotEmpty(message = "L'email é Obbligatoria")
    public String cemail;


}
