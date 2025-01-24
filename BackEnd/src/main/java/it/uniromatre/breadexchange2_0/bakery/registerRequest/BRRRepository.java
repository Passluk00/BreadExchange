package it.uniromatre.breadexchange2_0.bakery.registerRequest;

import it.uniromatre.breadexchange2_0.user.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BRRRepository extends JpaRepository<BakeryRegisterRequest,Integer> {


    Page<BakeryRegisterRequest> getAllByEnable(Pageable pageable, boolean enable);

    @Query("""
            select b
            from BakeryRegisterRequest as b
            where b.enable = false
    """)
    Page<BakeryRegisterRequest> findByEnable(Pageable pageable);

    @Query("""
        SELECT b
        FROM BakeryRegisterRequest b
        WHERE LOWER(b.name) LIKE LOWER(CONCAT('%', :name, '%')) and b.enable = false
    """)
    Page<BakeryRegisterRequest> findByName(Pageable pageable, String name);
}
