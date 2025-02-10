package it.uniromatre.breadexchange2_0.bakery;

import it.uniromatre.breadexchange2_0.bakery.fav.BakeryFav;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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


    public BakeryNavBarResponse toBakeryNavBar(Bakery bac){
        return new BakeryNavBarResponse(
                bac.getId(),
                bac.getName(),
                bac.getUrl_picture()
        );
    }

    public List<BakeryNavBarResponse> toBakeryNavBar(List<Bakery> bacs){
        return bacs.stream().map(this::toBakeryNavBar)
                .collect(Collectors.toList());
    }


    public BakeryFav toBakeryFav(Bakery bac){
        return new BakeryFav(
                bac.getId(),
                bac.getName(),
                bac.getUrl_picture(),
                bac.getOpenStatus()
        );
    }

    public RandomDataBakeryCarosello toRandomDataBakeryCarosello(Bakery bac){
        return new RandomDataBakeryCarosello(
                bac.getId(),
                bac.getUrl_backImg(),
                bac.getName(),
                bac.getDescription()
        );
    }

    public List<RandomDataBakeryCarosello> toRandomDataBakeryCarosello(List<Bakery> bacs){
        return bacs.stream().map(this::toRandomDataBakeryCarosello)
                .collect(Collectors.toList());
    }






}
