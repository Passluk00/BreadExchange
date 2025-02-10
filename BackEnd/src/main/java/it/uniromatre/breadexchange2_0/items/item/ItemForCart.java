package it.uniromatre.breadexchange2_0.items.item;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
public class ItemForCart {

    private Integer id;

    private String name;

    private String description;

    private String img;

    private BigDecimal price;

    private Integer quantity;

}
