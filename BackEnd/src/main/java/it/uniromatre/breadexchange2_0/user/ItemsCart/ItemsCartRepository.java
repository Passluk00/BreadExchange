package it.uniromatre.breadexchange2_0.user.ItemsCart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemsCartRepository extends JpaRepository<ItemsCart, Integer> {
}
