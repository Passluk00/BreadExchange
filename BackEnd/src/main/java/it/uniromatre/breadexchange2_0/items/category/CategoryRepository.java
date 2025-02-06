package it.uniromatre.breadexchange2_0.items.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("""
    select c
    from Category c
    where c.owner = :id
    """)
    Category findByOwner(Integer id);

    @Query("""
    select c
    from Category c
    where c.owner = :id
    """)
    List<Category> findAllByOwner(Integer id);


    @Query("""
    select c
    from Category c
    where c.id = :id
    """)
    Category getCategoryById(Integer id);

}
