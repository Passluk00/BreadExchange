package it.uniromatre.breadexchange2_0.items.item;

import it.uniromatre.breadexchange2_0.bakery.Bakery;
import it.uniromatre.breadexchange2_0.bakery.RandomDataBakeryResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemMapper {

    public Item fromRequestToItem(ItemRequest req){
        return Item.builder()
                .name(req.getName())
                .description(req.getDescription())
                .img(null)
                .price(req.getPrice()).build();
    }



    public RandomDataBakeryResponse fromRandom(Item item){
        return  RandomDataBakeryResponse.builder()
                .id(item.getId())
                .url(item.getImg())
                .build();
    }


    public ItemForCart toCart(Item item){
        return ItemForCart.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .img(item.getImg())
                .price(item.getPrice())
                .quantity(0)
                .build();
    }

}
