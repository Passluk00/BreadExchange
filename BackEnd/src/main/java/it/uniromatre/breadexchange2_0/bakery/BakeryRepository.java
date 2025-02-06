package it.uniromatre.breadexchange2_0.bakery;

import it.uniromatre.breadexchange2_0.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BakeryRepository extends JpaRepository<Bakery, Integer> {

    Bakery findByOwner(User user);


    Bakery getBakeryById(Integer id);


    @Query("""
     select b
     from Bakery as b
     """)
    Page<Bakery> getAll(Pageable pageable);

}
