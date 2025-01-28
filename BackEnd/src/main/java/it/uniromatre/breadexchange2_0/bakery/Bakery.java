package it.uniromatre.breadexchange2_0.bakery;

import it.uniromatre.breadexchange2_0.bakery.Week.Week;
import it.uniromatre.breadexchange2_0.bakery.contact.ContactInfo;
import it.uniromatre.breadexchange2_0.bakery.hours.WeekDay;
import it.uniromatre.breadexchange2_0.user.User;
import it.uniromatre.breadexchange2_0.user.address.Address;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="bakery")
public class Bakery {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private String description;

    @OneToOne
    private User owner;

    @ManyToOne
    private Address address;

    private String url_picture;

    private String url_backImg;

    @OneToOne
    private ContactInfo contactInfo;                // Dati di contatto

    private String openIngHours;

    /*

    @OneToMany(mappedBy = "bakery", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders

     */

    @OneToOne
    private Week week;

    @ElementCollection
    @CollectionTable(name = "user_images")          // Nome della tabella per la collezione
    @Column(name = "image_url")                     // Nome della colonna per gli URL
    private List<String> images;

    private LocalDateTime registrationDate;

    private Boolean openStatus;

    private Boolean abilitato;


}
