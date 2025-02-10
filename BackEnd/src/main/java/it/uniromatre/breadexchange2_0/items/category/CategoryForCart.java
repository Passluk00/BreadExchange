package it.uniromatre.breadexchange2_0.items.category;

import it.uniromatre.breadexchange2_0.bakery.BakeryfrontEndResponse;
import it.uniromatre.breadexchange2_0.items.item.ItemForCart;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class CategoryForCart {

    Integer id;

    Integer owner;

    String name;

    List<ItemForCart> items;

}
