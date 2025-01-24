package it.uniromatre.breadexchange2_0.bakery;

import org.springframework.stereotype.Service;

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
                bak.getAddress()
        );
    }

    //TODO Cambiare valore con imagini oggetti

    public RandomDataBakeryResponse fromRandom(Bakery bac){
        return new RandomDataBakeryResponse(
                bac.getId(),
                bac.getUrl_picture()
        );
    }

/*      TODO Finire funzione elementi random
    public RandomDataBakeryResponse toRandomData(List<Bakery> bac){
        return bac.stream(this::fromRandom);
    }
*/

    public BakeryfrontEndResponse fromBakeryToFrontEnd(Bakery bac){
        return new BakeryfrontEndResponse(
                bac.getId(),
                bac.getName(),
                bac.getDescription(),
                bac.getOwner().getId(),
                bac.getAddress(),
                bac.getUrl_picture(),
                bac.getContactInfo(),
                bac.getOpenIngHours()
        );
    }



}
