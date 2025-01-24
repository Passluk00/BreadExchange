package it.uniromatre.breadexchange2_0.user;

import it.uniromatre.breadexchange2_0.bakery.registerRequest.BakeryRegisterRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {


    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    User findUserById(Integer id);

    @Query(value = "SELECT i FROM User i ORDER BY function('RAND')")
    User findRandom();

    @Query("""
        SELECT u
        FROM User u
        WHERE u.username ILIKE CONCAT('%', :username, '%')
    """)
    Page<User> findAllByUsername(Pageable pageable, String username);
}
