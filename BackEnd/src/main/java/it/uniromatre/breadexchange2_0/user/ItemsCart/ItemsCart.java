package it.uniromatre.breadexchange2_0.user.ItemsCart;

import it.uniromatre.breadexchange2_0.items.item.Item;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemsCart {

    public ItemsCart(ItemsCart i){
        this.id = i.id;
        this.quantity = i.quantity;
        this.details = i.details;
        this.subTotal = i.subTotal;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Integer quantity;

    @ManyToOne
    private Item details;

    private BigDecimal subTotal;

    @PrePersist
    @PreUpdate
    private void calculateSubTotal() {
        if (this.details != null && this.details.getPrice() != null && this.quantity != null) {
            this.subTotal = this.details.getPrice()
                    .multiply(BigDecimal.valueOf(this.quantity))
                    .setScale(2, RoundingMode.HALF_UP);
        } else {
            this.subTotal = BigDecimal.ZERO;
        }
    }
}
