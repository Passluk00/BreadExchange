package it.uniromatre.breadexchange2_0.bakery.hours;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Repository
public interface WeekDayRepository extends JpaRepository<WeekDay,Integer> {

    @Modifying
    @Query("""
    update WeekDay wd
    set wd.dayOfWeek = :dayOfWeek, wd.morningOpening = :morningOpening, wd.morningClosing = :morningClosing, wd.eveningOpening = :eveningOpening, wd.eveningClosing = :eveningClosing, wd.isClosed = :isClosed, wd.isAllDayOpen = :isAllDayOpen
    where wd.id = :id
    """)
    void SaveByWeek(@Param("id")Integer id, @Param("dayOfWeek")String dayOfWeek , @Param("morningOpening")LocalTime morningOpening , @Param("morningClosing")LocalTime morningClosing, @Param("eveningOpening")LocalTime eveningOpening, @Param("eveningClosing")LocalTime eveningClosing, @Param("isClosed")boolean isClosed, @Param("isAllDayOpen")boolean isAllDayOpen );

}
