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

    Page<BakeryRegisterRequest> findAll(Pageable pageable);


    void deleteByUser(User user);

    @Modifying
    @Query("""
            delete
            from BakeryRegisterRequest
            where user = :id
    """)
    @Transactional
    void delUser(User id);

}
