package it.uniromatre.breadexchange2_0.bakery.Week;

import it.uniromatre.breadexchange2_0.bakery.Bakery;
import it.uniromatre.breadexchange2_0.bakery.BakeryRepository;
import it.uniromatre.breadexchange2_0.bakery.BakeryService;
import it.uniromatre.breadexchange2_0.bakery.hours.WeekDay;
import it.uniromatre.breadexchange2_0.bakery.hours.WeekDayRepository;
import it.uniromatre.breadexchange2_0.bakery.hours.WeekDayService;
import it.uniromatre.breadexchange2_0.role.Role;
import it.uniromatre.breadexchange2_0.user.User;
import it.uniromatre.breadexchange2_0.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WeekService {

    private final UserRepository userRepository;
    private final BakeryRepository bakeryRepository;
    private final WeekDayService weekDayService;
    private final WeekRepository weekRepository;
    private final WeekMapper weekMapper;
    private final WeekDayRepository weekDayRepository;


    // Utilizzata in creazione bakery

    public Week saveAllClosed(){

        // creazione settimana Chiusa

        WeekDay lun = new WeekDay("Lunedì");
        WeekDay mar = new WeekDay("Martedì");
        WeekDay mer = new WeekDay("Mercoledì");
        WeekDay gio = new WeekDay("Giovedì");
        WeekDay ven = new WeekDay("Venerdì");
        WeekDay sab = new WeekDay("Sabato");
        WeekDay dom = new WeekDay("Deomenica");

        Week week = Week.builder()
                .lun(lun)
                .mar(mar)
                .mer(mer)
                .gio(gio)
                .ven(ven)
                .sab(sab)
                .dom(dom)
                .build();



        weekDayService.saveAllDay(lun,mar,mer,gio,ven,sab,dom);
        weekRepository.save(week);
        return week;
    }


    // utilizzate per richieste front-end


            // modifica la settimana
    public void modificaGiorno(Authentication connectedUser, ModifyWeekRequest newWeek, Integer idBakery) {

        // controllo validita richiesta
        if (newWeek == null) {
            throw new RuntimeException("La richiesta è null");
        }


        // controllo validita Bakery
        Bakery bac = bakeryRepository.findById(idBakery).orElseThrow(() -> new RuntimeException("Bakery not found with id: "+ idBakery));


        // controllo validita utente
        User user = ((User)connectedUser.getPrincipal());


        // controllo se utente è owner
        if(!bac.getOwner().getId().equals(user.getId())){
            throw new RuntimeException("Non sei il propietario azione negata");
        }


        // mappo la nuova richiesta come New Week
        Week nuova = weekMapper.toWeek(newWeek);


        // weekDayService.updateWeekIfChanged(vecchia,nuova,bac);

        this.updateAllWeek(nuova, bac);




    }


    private void updateAllWeek(Week nuova, Bakery bac){

        weekDayRepository.save(nuova.getLun());
        weekDayRepository.save(nuova.getMar());
        weekDayRepository.save(nuova.getMer());
        weekDayRepository.save(nuova.getGio());
        weekDayRepository.save(nuova.getVen());
        weekDayRepository.save(nuova.getSab());
        weekDayRepository.save(nuova.getDom());

        weekRepository.save(nuova);

        bac.setWeek(nuova);
        bakeryRepository.save(bac);

    }




}
