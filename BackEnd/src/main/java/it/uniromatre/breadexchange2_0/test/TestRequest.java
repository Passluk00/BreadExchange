package it.uniromatre.breadexchange2_0.test;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record TestRequest(

        Integer id,
        @NotNull(message = "100")
        @NotEmpty(message = "100")
        String name,
        @NotNull(message = "101")
        @NotEmpty(message = "101")
        String description

) { }
