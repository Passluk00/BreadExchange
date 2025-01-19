package it.uniromatre.breadexchange2_0.user;


import it.uniromatre.breadexchange2_0.user.address.Address;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserFrontEndResponse {
    int id;
    String username;
    String email;
    String url_picture;
    String url_back;
    Address address;
}
