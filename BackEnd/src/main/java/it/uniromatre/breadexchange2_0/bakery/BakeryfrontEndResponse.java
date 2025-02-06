package it.uniromatre.breadexchange2_0.bakery;


import it.uniromatre.breadexchange2_0.bakery.contact.ContactInfo;
import it.uniromatre.breadexchange2_0.items.category.Category;
import it.uniromatre.breadexchange2_0.user.address.Address;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
    public String Back_img;
    public ContactInfo info;
    public List<Category> categorys;

}

