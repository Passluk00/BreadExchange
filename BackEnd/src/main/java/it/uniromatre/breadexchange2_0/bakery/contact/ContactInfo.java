package it.uniromatre.breadexchange2_0.bakery.contact;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ContactInfo {

    @Id
    @GeneratedValue
    private Integer id;

    private String email;

    private String phone;

    private String twitter;  // ora X

    private String facebook;

    private String instagram;


}
