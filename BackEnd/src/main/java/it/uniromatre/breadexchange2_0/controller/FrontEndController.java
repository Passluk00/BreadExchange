package it.uniromatre.breadexchange2_0.controller;

import it.uniromatre.breadexchange2_0.bakery.BakeryService;
import it.uniromatre.breadexchange2_0.bakery.BakeryfrontEndResponse;
import it.uniromatre.breadexchange2_0.bakery.PlanAddBkery;
import it.uniromatre.breadexchange2_0.bakery.RandomDataBakeryResponse;
import it.uniromatre.breadexchange2_0.bakery.Week.Week;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("frontEnd")
@RequiredArgsConstructor
public class FrontEndController {


    private final BakeryService bakeryService;


    @GetMapping("/getRandomData")
    public List<RandomDataBakeryResponse> getRandomData(
            @RequestParam(name = "id") Integer id
    ){
        return this.bakeryService.getRandomData(id);
    }

    @GetMapping("/bakery/{id}")
    public ResponseEntity<BakeryfrontEndResponse> bakeryDetail(
            @PathVariable Integer id
    ){
        return ResponseEntity.ok(bakeryService.getData(id));
    }

    @GetMapping("/getPlaneAddress")
    public ResponseEntity<PlanAddBkery> getPlaneAdd(
            @RequestParam(name = "id") int id
    ){
        return ResponseEntity.ok( this.bakeryService.getPlaneAdd(id) );
    }

    @GetMapping("/getWeek")
    public ResponseEntity<Week> getWeek(
            @RequestParam(name = "idBakery") Integer idBakery
    ){
        return ResponseEntity.ok(bakeryService.getWeek(idBakery));
    }


}
