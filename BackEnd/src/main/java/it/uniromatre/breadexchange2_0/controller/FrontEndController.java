package it.uniromatre.breadexchange2_0.controller;

import it.uniromatre.breadexchange2_0.bakery.BakeryService;
import it.uniromatre.breadexchange2_0.bakery.BakeryfrontEndResponse;
import it.uniromatre.breadexchange2_0.bakery.PlanAddBkery;
import it.uniromatre.breadexchange2_0.bakery.RandomDataBakeryResponse;
import it.uniromatre.breadexchange2_0.bakery.Week.Week;
import it.uniromatre.breadexchange2_0.items.category.CategoryService;
import it.uniromatre.breadexchange2_0.items.category.ListCategoryFrontEnd;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("frontEnd")
@RequiredArgsConstructor
public class FrontEndController {


    private final BakeryService bakeryService;
    private final CategoryService categoryService;


    @GetMapping("/getRandomData")
    public List<RandomDataBakeryResponse> getRandomData(
            @RequestParam(name = "idBac") Integer idBac
    ){
        return this.bakeryService.getRandomData(idBac);
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

    @PostMapping("/testCategory")
    public ResponseEntity<?> testCategory(){
        this.categoryService.generaVuoto();
        return ResponseEntity.accepted().build();
    }


    @GetMapping("/getCategory")
    public ResponseEntity<?> getCategory(
            @RequestParam(name = "owner") Integer id
    ){
        return ResponseEntity.ok(this.categoryService.getCategory(id));
    }

    @GetMapping("/getAllCategory")
    public ResponseEntity<ListCategoryFrontEnd> getAllCategory(
            @RequestParam(name = "owner") Integer id
    ){
        return ResponseEntity.ok(this.categoryService.getAllCategory(id));
    }


    @GetMapping("/checkOwner")
    public ResponseEntity<Boolean> checkOwner(
            @RequestParam(name = "idBack") Integer idBac,
            Authentication connectedUser
    ){
        try{
            boolean isOwner = this.bakeryService.checkOwner(idBac,connectedUser);
            return ResponseEntity.ok(isOwner);
        } catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }


}










