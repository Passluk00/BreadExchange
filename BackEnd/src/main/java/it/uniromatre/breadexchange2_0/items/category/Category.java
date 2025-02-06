package it.uniromatre.breadexchange2_0.items.category;

import it.uniromatre.breadexchange2_0.bakery.Bakery;
import it.uniromatre.breadexchange2_0.items.item.Item;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name="_category")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Integer owner;

    private String name;

    @OneToMany
    private List<Item> items;


}
