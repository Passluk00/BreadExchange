package it.uniromatre.breadexchange2_0.token;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class tokenVerifyRequest {

    @NotNull(message = "101")
    @NotEmpty(message = "101")
    public String token;
}
