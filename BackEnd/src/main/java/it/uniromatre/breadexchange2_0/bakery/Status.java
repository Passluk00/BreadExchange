package it.uniromatre.breadexchange2_0.bakery;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Status {

    private Integer id;

    private boolean status;

}
