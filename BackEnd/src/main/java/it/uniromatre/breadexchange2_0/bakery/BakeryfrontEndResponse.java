package it.uniromatre.breadexchange2_0.bakery;


import it.uniromatre.breadexchange2_0.bakery.contact.ContactInfo;
import it.uniromatre.breadexchange2_0.user.address.Address;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BakeryfrontEndResponse {

    public Integer id;
    public String name;
    public String desc;
    public Integer owner;
    public Address address;
    public String Logo;
    public ContactInfo info;
    public String openInHours;

}

// TODO aggiungere liste di categorie
// TODO Aggiungere Tutti gli oggetti in vendita
