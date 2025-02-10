package it.uniromatre.breadexchange2_0.bakery;

import it.uniromatre.breadexchange2_0.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BakeryRepository extends JpaRepository<Bakery, Integer> {

    Bakery findByOwner(User user);


    Bakery getBakeryById(Integer id);


    @Query("""
     select b
     from Bakery as b
     """)
    Page<Bakery> getAll(Pageable pageable);


    @Query("""
        SELECT b
        FROM Bakery b
        WHERE b.name ILIKE CONCAT('%', :name, '%')
    """)
    List<Bakery> findByName(String name);


   @Query("""
    select case when count(b) > 0 then true else false end
    from Bakery b
    where b.owner = :owner
    """)
    Boolean existsbyOwnerId(User owner);


    @Query(value = "SELECT * FROM bakery ORDER BY RANDOM() LIMIT 3", nativeQuery = true)
    List<Bakery> getForCarosello();


}
