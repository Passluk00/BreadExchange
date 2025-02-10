package it.uniromatre.breadexchange2_0.user.order;

import it.uniromatre.breadexchange2_0.user.ItemsCart.ItemsCart;
import it.uniromatre.breadexchange2_0.user.address.Address;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
public class OrderFrontEnd {

   private Integer id;

   private Integer idBac;

   private String nameAzz;

   private Address address;

   private List<ItemsCart> items;

   private boolean accepted;

   private String status;

   private BigDecimal TotalPrice;

}
