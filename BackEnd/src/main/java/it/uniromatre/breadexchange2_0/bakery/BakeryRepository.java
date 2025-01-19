package it.uniromatre.breadexchange2_0.bakery;

import it.uniromatre.breadexchange2_0.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BakeryRepository extends JpaRepository<Bakery, Integer> {

    Bakery findByOwner(User user);

}
