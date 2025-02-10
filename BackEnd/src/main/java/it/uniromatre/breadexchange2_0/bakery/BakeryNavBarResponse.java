package it.uniromatre.breadexchange2_0.bakery;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BakeryNavBarResponse {

    Integer id;
    String name;
    String picture;

}
