package it.uniromatre.breadexchange2_0.user;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class UpdateUserRequest {

    @NotNull(message = "101")
    @NotEmpty(message = "101")
    String description;

    // aggiungere i vari campi da modificare


}
