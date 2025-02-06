package it.uniromatre.breadexchange2_0.items.item;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class ItemRequest {

    @NotEmpty(message = "Il Nome Dell' Oggetto é obligatorio")
    @NotBlank(message = "Il Nome Dell' Oggetto é obligatorio")
    private String name;

    @NotEmpty(message = "La descrizione Dell' Oggetto é obligatorio")
    @NotBlank(message = "La Descrizione Dell' Oggetto é obligatorio")
    private String description;

    @NotEmpty(message = "Il Prezzo Dell' Oggetto é obligatorio")
    @NotBlank(message = "Il Prezzo Dell' Oggetto é obligatorio")
    private BigDecimal price;

}
