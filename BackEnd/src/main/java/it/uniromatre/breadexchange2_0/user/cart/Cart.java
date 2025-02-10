package it.uniromatre.breadexchange2_0.user.cart;

import it.uniromatre.breadexchange2_0.user.ItemsCart.ItemsCart;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Integer IdBac;

    @OneToMany
    private List<ItemsCart> items;

    private BigDecimal total;


    public void updateTotal() {
        if (this.items == null || this.items.isEmpty()) {
            this.total = BigDecimal.ZERO;
        } else {
            this.total = this.items.stream()
                    .map(ItemsCart::getSubTotal)
                    .filter(Objects::nonNull) // Evita errori con valori null
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .setScale(2, RoundingMode.HALF_UP);
        }
    }


}
