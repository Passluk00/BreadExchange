package it.uniromatre.breadexchange2_0.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JwtTokenRepository extends JpaRepository<JwtToken, Integer> {

    Optional<JwtToken> findByToken(String token);

    List<JwtToken> findAllValidTokenByUserId(Integer id);

}
