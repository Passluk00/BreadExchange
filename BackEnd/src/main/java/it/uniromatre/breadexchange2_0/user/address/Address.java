package it.uniromatre.breadexchange2_0.user.address;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Getter
@Setter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private String telNumber;

    private String country;

    private String state;

    private String provincia;

    private String city;

    private String postalCode;

    private String street;

    private String number;



}
