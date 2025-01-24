package it.uniromatre.breadexchange2_0.controller;

import it.uniromatre.breadexchange2_0.bakery.BakeryService;
import it.uniromatre.breadexchange2_0.bakery.BakeryfrontEndResponse;
import it.uniromatre.breadexchange2_0.bakery.RandomDataBakeryResponse;
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


}
