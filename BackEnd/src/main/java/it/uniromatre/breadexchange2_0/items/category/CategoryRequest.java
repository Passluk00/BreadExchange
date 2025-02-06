package it.uniromatre.breadexchange2_0.items.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class CategoryRequest {

    @NotEmpty(message = "Il Nome Della Categoria é obligatorio")
    @NotBlank(message = "Il Nome Della Categoria é obligatorio")
    private String name;

}
