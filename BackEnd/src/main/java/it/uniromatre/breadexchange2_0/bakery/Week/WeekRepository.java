package it.uniromatre.breadexchange2_0.bakery.Week;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WeekRepository extends JpaRepository<Week, Integer> {



}
