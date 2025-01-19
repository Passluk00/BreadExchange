package it.uniromatre.breadexchange2_0.bakery.registerRequest;


import it.uniromatre.breadexchange2_0.user.User;
import it.uniromatre.breadexchange2_0.user.address.Address;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class BakeryRegisterRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToOne
    private User user;

    private String name;
    private String description;
    private String email_azz;
    private String phone_azz;
    private String twitter;
    private String facebook;
    private String Instagram;

    @ManyToOne
    private Address address;
}
