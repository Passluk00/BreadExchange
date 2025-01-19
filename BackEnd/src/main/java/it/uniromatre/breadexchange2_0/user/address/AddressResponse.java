package it.uniromatre.breadexchange2_0.user.address;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressResponse {

    String name;
    String telNumber;
    String country;
    String state;
    String provincia;
    String city;
    String postalCode;
    String street;
    String number;



}
