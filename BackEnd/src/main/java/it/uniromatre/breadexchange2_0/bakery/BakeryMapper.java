package it.uniromatre.breadexchange2_0.bakery;

import it.uniromatre.breadexchange2_0.items.item.Item;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BakeryMapper {

    public BakeryResponse fromBakery(Bakery bak){

        if( bak == null){
            return null;
        }
        return new BakeryResponse(
                bak.getId(),
                bak.getName(),
                bak.getOwner(),
                bak.getUrl_picture(),
                bak.getAbilitato(),
                bak.getAddress(),
                bak.getCategories()
        );
    }






    public BakeryfrontEndResponse fromBakeryToFrontEnd(Bakery bac){
        return new BakeryfrontEndResponse(
                bac.getId(),
                bac.getName(),
                bac.getDescription(),
                bac.getOwner().getId(),
                bac.getAddress(),
                bac.getUrl_picture(),
                bac.getUrl_backImg(),
                bac.getContactInfo(),
                bac.getCategories()
        );
    }



}
