package it.uniromatre.breadexchange2_0.items.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {



    @Query("""
           select I
           from Item I
           where I.id = :id
    """)
    Item getItemById(Integer id);
}
