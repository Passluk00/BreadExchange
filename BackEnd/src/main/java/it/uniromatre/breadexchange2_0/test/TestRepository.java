package it.uniromatre.breadexchange2_0.test;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<TestClass, Integer> {

    @Query("""
    SELECT test
    FROM TestClass test
    WHERE test.owner != :userId
    """)
    Page<TestClass> findAllDisplayableTest(Pageable pageable, Integer userId);


    @Query("""
    SELECT test
    FROM TestClass test
    WHERE test.owner = :userId
    """)
    Page<TestClass> findAllByOwner(Pageable pageable, Integer userId);

}
