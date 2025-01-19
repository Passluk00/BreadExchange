package it.uniromatre.breadexchange2_0.user.address;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record NewAddress (

        Integer id,

        @NotEmpty(message = "Il Nome è Obbligatorio")
        @NotBlank(message = "Il Nome è Obbligatorio")
        String name,

        @NotEmpty(message = "Il Paese è Obbligatorio")
        @NotBlank(message = "Il Paese è Obbligatorio")
        String country,

        @NotEmpty(message = "Lo stato è Obbligatorio")
        @NotBlank(message = "Lo stato è Obbligatorio")
        String state,

        @NotEmpty(message = "La Provincia è Obbligatoria")
        @NotBlank(message = "La Provincia è Obbligatoria")
        String provincia,

        @NotEmpty(message = "La Città è Obbligatoria")
        @NotBlank(message = "La Città è Obbligatoria")
        String city,

        @NotEmpty(message = "Il codice Postale è Obbligatorio")
        @NotBlank(message = "Il codice Postale è Obbligatorio")
        String postalCode,

        @NotEmpty(message = "La Strada è Obbligatoria")
        @NotBlank(message = "La Strada è Obbligatoria")
        String street,

        @NotEmpty(message = "Il Numero Civico è Obbligatorio")
        @NotBlank(message = "Il Numero Civico è Obbligatorio")
        String number,

        @NotEmpty(message = "Il Numero Telefonico è Obbligatorio")
        @NotBlank(message = "Il Numero Telefonico è Obbligatorio")
        String telNumber




){}
