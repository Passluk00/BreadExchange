package it.uniromatre.breadexchange2_0.user.order;

import it.uniromatre.breadexchange2_0.bakery.Bakery;
import it.uniromatre.breadexchange2_0.user.ItemsCart.ItemsCart;
import it.uniromatre.breadexchange2_0.user.User;
import it.uniromatre.breadexchange2_0.user.address.Address;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@Table(name="_order")
@AllArgsConstructor
@NoArgsConstructor
public class Order {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    private Bakery bakery;
    //private Integer idBac;


    @ManyToOne
    private User user;
    //private Integer idCustomer;

    @ManyToOne
    private Address address;



    @OneToMany(cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<ItemsCart> items;
    /*
    @OneToMany
    private List<ItemsCart> items = new ArrayList<>();
     */


    private boolean accepted;

    private String status;

    private BigDecimal totalPrice;

    public void calculateTotalPrice() {
        if (this.items != null) {
            this.totalPrice = this.items.stream()
                    .map(ItemsCart::getSubTotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .setScale(2, RoundingMode.HALF_UP);
        } else {
            this.totalPrice = BigDecimal.ZERO;
        }
    }



}
