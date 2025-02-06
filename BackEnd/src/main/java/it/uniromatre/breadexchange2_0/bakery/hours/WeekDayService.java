package it.uniromatre.breadexchange2_0.bakery.hours;

import it.uniromatre.breadexchange2_0.bakery.Bakery;
import it.uniromatre.breadexchange2_0.bakery.BakeryRepository;
import it.uniromatre.breadexchange2_0.bakery.Week.Week;
import it.uniromatre.breadexchange2_0.bakery.Week.WeekRepository;
import it.uniromatre.breadexchange2_0.user.User;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WeekDayService {

    private final WeekDayRepository weekDayRepository;
    private final BakeryRepository bakeryRepository;
    private final WeekRepository weekRepository;


    public void saveOneDay(WeekDay day){
        weekDayRepository.save(day);
    }

    public void saveAllDay(WeekDay lun,
                           WeekDay mar,
                           WeekDay mer,
                           WeekDay gio,
                           WeekDay ven,
                           WeekDay sab,
                           WeekDay dom){
        weekDayRepository.save(lun);
        weekDayRepository.save(mar);
        weekDayRepository.save(mer);
        weekDayRepository.save(gio);
        weekDayRepository.save(ven);
        weekDayRepository.save(sab);
        weekDayRepository.save(dom);
    }




    @Transactional
    public void updateWeekIfChanged(Week oldWeek, Week newWeek , Bakery bac) {

        boolean isChanged = false;

        // Confronta e aggiorna ogni giorno della settimana
        isChanged |= compareAndUpdateWeekDay(oldWeek.getLun(), newWeek.getLun());
        isChanged |= compareAndUpdateWeekDay(oldWeek.getMar(), newWeek.getMar());
        isChanged |= compareAndUpdateWeekDay(oldWeek.getMer(), newWeek.getMer());
        isChanged |= compareAndUpdateWeekDay(oldWeek.getGio(), newWeek.getGio());
        isChanged |= compareAndUpdateWeekDay(oldWeek.getVen(), newWeek.getVen());
        isChanged |= compareAndUpdateWeekDay(oldWeek.getSab(), newWeek.getSab());
        isChanged |= compareAndUpdateWeekDay(oldWeek.getDom(), newWeek.getDom());

        // Salva il week aggiornato se c'è stata una modifica
        if (isChanged) {

            bac.setWeek(newWeek);
            weekRepository.save(newWeek);
            bakeryRepository.save(bac);
        }
    }


    private boolean compareAndUpdateWeekDay(WeekDay oldDay, WeekDay newDay) {
        boolean isChanged = false;

        if (!equals(oldDay.getMorningOpening(), newDay.getMorningOpening())) {
            oldDay.setMorningOpening(newDay.getMorningOpening());
            isChanged = true;
            weekDayRepository.SaveByWeek(oldDay.getId(), newDay.getDayOfWeek(), newDay.getMorningOpening(), newDay.getMorningClosing(), newDay.getEveningOpening(), newDay.getEveningClosing(), newDay.isClosed(), newDay.isAllDayOpen());

        }

        if (!equals(oldDay.getMorningClosing(), newDay.getMorningClosing())) {
            oldDay.setMorningClosing(newDay.getMorningClosing());
            isChanged = true;
            weekDayRepository.SaveByWeek(oldDay.getId(), newDay.getDayOfWeek(), newDay.getMorningOpening(), newDay.getMorningClosing(), newDay.getEveningOpening(), newDay.getEveningClosing(), newDay.isClosed(), newDay.isAllDayOpen());
        }

        if (!equals(oldDay.getEveningOpening(), newDay.getEveningOpening())) {
            oldDay.setEveningOpening(newDay.getEveningOpening());
            isChanged = true;
            weekDayRepository.SaveByWeek(oldDay.getId(), newDay.getDayOfWeek(), newDay.getMorningOpening(), newDay.getMorningClosing(), newDay.getEveningOpening(), newDay.getEveningClosing(), newDay.isClosed(), newDay.isAllDayOpen());
        }

        if (!equals(oldDay.getEveningClosing(), newDay.getEveningClosing())) {
            oldDay.setEveningClosing(newDay.getEveningClosing());
            isChanged = true;
            weekDayRepository.SaveByWeek(oldDay.getId(), newDay.getDayOfWeek(), newDay.getMorningOpening(), newDay.getMorningClosing(), newDay.getEveningOpening(), newDay.getEveningClosing(), newDay.isClosed(), newDay.isAllDayOpen());
        }

        if (oldDay.isClosed() != newDay.isClosed()) {
            oldDay.setClosed(newDay.isClosed());
            isChanged = true;
            weekDayRepository.SaveByWeek(oldDay.getId(), newDay.getDayOfWeek(), newDay.getMorningOpening(), newDay.getMorningClosing(), newDay.getEveningOpening(), newDay.getEveningClosing(), newDay.isClosed(), newDay.isAllDayOpen());
        }

        if (oldDay.isAllDayOpen() != newDay.isAllDayOpen()) {
            oldDay.setAllDayOpen(newDay.isAllDayOpen());
            isChanged = true;
            weekDayRepository.SaveByWeek(oldDay.getId(), newDay.getDayOfWeek(), newDay.getMorningOpening(), newDay.getMorningClosing(), newDay.getEveningOpening(), newDay.getEveningClosing(), newDay.isClosed(), newDay.isAllDayOpen());
        }

        return isChanged;
    }


    private boolean equals(Object obj1, Object obj2) {
        if (obj1 == null && obj2 == null) {
            return true;
        }
        if (obj1 == null || obj2 == null) {
            return false;
        }
        return obj1.equals(obj2);
    }



}
