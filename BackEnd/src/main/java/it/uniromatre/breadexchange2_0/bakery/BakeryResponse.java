package it.uniromatre.breadexchange2_0.bakery;

import it.uniromatre.breadexchange2_0.user.User;
import it.uniromatre.breadexchange2_0.user.address.Address;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BakeryResponse {

    Integer id;
    String name;
    User owner;
    String url_picture;
    Boolean enable;
    Address address;



}
