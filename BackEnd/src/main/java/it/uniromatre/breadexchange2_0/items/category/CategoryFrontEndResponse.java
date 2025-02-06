package it.uniromatre.breadexchange2_0.items.category;


import it.uniromatre.breadexchange2_0.bakery.BakeryfrontEndResponse;
import it.uniromatre.breadexchange2_0.items.item.Item;
import lombok.*;
import java.util.List;

@Getter
@Setter
@Builder
public class CategoryFrontEndResponse {

    Integer id;

    BakeryfrontEndResponse owner;

    String name;

    List<Item> items;


}
