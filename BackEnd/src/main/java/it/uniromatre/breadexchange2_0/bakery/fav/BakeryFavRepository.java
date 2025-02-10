package it.uniromatre.breadexchange2_0.bakery.fav;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BakeryFavRepository extends JpaRepository<BakeryFav, Integer> {
}
