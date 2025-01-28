package it.uniromatre.breadexchange2_0.controller;


import it.uniromatre.breadexchange2_0.bakery.BakeryService;
import it.uniromatre.breadexchange2_0.bakery.Week.ModifyWeekRequest;
import it.uniromatre.breadexchange2_0.bakery.Week.Week;
import it.uniromatre.breadexchange2_0.bakery.Week.WeekService;
import it.uniromatre.breadexchange2_0.bakery.hours.WeekDay;
import it.uniromatre.breadexchange2_0.bakery.hours.WeekDayService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("bakery")
@AllArgsConstructor
public class BakeryController {

    private final BakeryService bakeryService;
    private final WeekService weekService;
    private final WeekDayService weekDayService;




    @PostMapping("/updateWeek")
    public ResponseEntity<?> updateWeek(
            @RequestBody ModifyWeekRequest request,
            @RequestParam(name = "IdBakery") Integer idBackery,
            Authentication connectedUser
            ){
        this.weekService.modificaGiorno(connectedUser,request,idBackery);
        return ResponseEntity.accepted().build();
    }




}

