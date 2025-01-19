package it.uniromatre.breadexchange2_0.user.address;
import it.uniromatre.breadexchange2_0.user.User;
import org.springframework.stereotype.Service;

@Service
public class AddressMapper {


    public Address toAddress(NewAddress request) {
        return Address.builder()
                .id(request.id())
                .name(request.name())
                .country(request.country())
                .state(request.state())
                .provincia(request.provincia())
                .city(request.city())
                .postalCode(request.postalCode())
                .street(request.street())
                .number(request.number())
                .telNumber(request.telNumber())
                .build();
    }

    //TODO controllare da qui

    public AddressResponse fromAddress(User user){
        Address add = user.getAddress();
        return AddressResponse.builder()
                .name(add.getName())
                .telNumber(add.getTelNumber())
                .country(add.getCountry())
                .state(add.getState())
                .provincia(add.getProvincia())
                .city(add.getCity())
                .postalCode(add.getPostalCode())
                .street(add.getStreet())
                .number(add.getNumber())
                .build();
    }

}
