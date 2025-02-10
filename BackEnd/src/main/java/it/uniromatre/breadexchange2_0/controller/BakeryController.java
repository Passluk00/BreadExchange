package it.uniromatre.breadexchange2_0.controller;

import io.swagger.v3.oas.annotations.Parameter;
import it.uniromatre.breadexchange2_0.bakery.BakeryService;
import it.uniromatre.breadexchange2_0.bakery.ReqModDesc;
import it.uniromatre.breadexchange2_0.bakery.UpdateSocialBakery;
import it.uniromatre.breadexchange2_0.bakery.Week.ModifyWeekRequest;
import it.uniromatre.breadexchange2_0.bakery.Week.Week;
import it.uniromatre.breadexchange2_0.bakery.Week.WeekService;
import it.uniromatre.breadexchange2_0.items.category.CategoryService;
import it.uniromatre.breadexchange2_0.items.item.ItemRequest;
import it.uniromatre.breadexchange2_0.items.item.ItemService;
import it.uniromatre.breadexchange2_0.user.address.NewAddress;
import it.uniromatre.breadexchange2_0.user.order.Order;
import it.uniromatre.breadexchange2_0.user.order.OrderFrontEnd;
import it.uniromatre.breadexchange2_0.user.order.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("bakery")
@AllArgsConstructor
public class BakeryController {


    private final WeekService weekService;
    private final CategoryService categoryService;
    private final ItemService itemService;
    private final BakeryService bakeryService;
    private final OrderService orderService;



    @PostMapping("/updateWeek")
    public ResponseEntity<?> updateWeek(
            @RequestBody ModifyWeekRequest request,
            @RequestParam(name = "IdBakery") Integer idBackery,
            Authentication connectedUser
            ){
        this.weekService.modificaGiorno(connectedUser,request,idBackery);
        return ResponseEntity.accepted().build();
    }


    // Crea Category
    @PostMapping("/newCategory")
    public ResponseEntity<?> newCategory(
            @RequestParam(name = "bakeryId") Integer idBac,
            @RequestParam(name = "nomeCat") String nameCat,
            Authentication connectedUser
            ){
        this.categoryService.newCategory(nameCat,idBac,connectedUser);
        return ResponseEntity.accepted().build();
    }


    // Cancella Categoria
    @DeleteMapping("/delCategory")
    public ResponseEntity<?> delCategory(
            @RequestParam(name = "idCat") Integer idCat,
            Authentication connectedUser
    ){
        this.categoryService.deleteCategory(idCat,connectedUser);
        return ResponseEntity.accepted().build();
    }


    // Crea Item
    @PostMapping(value = "/newItem", consumes = "multipart/form-data")
    public ResponseEntity<?> newItem(
            @RequestParam(name = "idBac") Integer idBac,
            @RequestParam(name = "idCat") Integer idCat,
            @RequestPart(name = "file",required = false) MultipartFile file,
            @RequestPart(name = "request") ItemRequest req,
            Authentication connectedUser
            ){
        this.itemService.newItem(req,connectedUser,idBac,idCat,file);
        return ResponseEntity.accepted().build();
    }


    // modififca Item
    @PostMapping("/modItem")
    public ResponseEntity<?> updateItem(
            @RequestParam(name = "idItem") Integer idItem,
            @RequestParam(name = "idBac") Integer idBac,
            @RequestBody ItemRequest req,
            Authentication connectedUser
    ){
        this.itemService.modificaItem(idBac,idItem,req,connectedUser);
        return ResponseEntity.accepted().build();
    }


    // Cancella Item
    @DeleteMapping("/delItem")
    public ResponseEntity<?> delItem(
            @RequestParam(name = "idItem") Integer idItem,
            @RequestParam(name = "idCat") Integer idCat,
            @RequestParam(name = "idBac") Integer idBac,
            Authentication connectedUser
    ){
        this.itemService.deleteItem(idItem,connectedUser,idBac, idCat);
        return ResponseEntity.accepted().build();
    }


    @PostMapping(value = "/upload-profile-picture", consumes = "multipart/form-data")
    @PreAuthorize("hasAuthority('bakery_owner:update')")
    public ResponseEntity<?> uploadProfilePicture(
            @Parameter()
            @RequestPart("file")MultipartFile file,
            @Parameter()
            @RequestParam(name = "idBac") Integer idBac,
            Authentication connectedUser
    ){
        this.bakeryService.uploadProfilePicture(file,connectedUser,idBac);
        return ResponseEntity.accepted().build();
    }

    @PostMapping(value = "/upload-back-picture", consumes = "multipart/form-data")
    @PreAuthorize("hasAuthority('bakery_owner:update')")
    public ResponseEntity<?> uploadBackPicture(
            @Parameter()
            @RequestPart("file")MultipartFile file,
            @Parameter()
            @RequestParam(name = "idBac") Integer idBac,
            Authentication connectedUser
    ){
        this.bakeryService.uploadBackPicture(file,connectedUser,idBac);
        return ResponseEntity.accepted().build();
    }


    @PostMapping("/addAddress")
    public ResponseEntity<?> addAddress(
            Authentication connectedUser,
            @RequestParam(name = "idBac") Integer idBac,
            @RequestBody NewAddress address
    ){
        this.bakeryService.addAddress(connectedUser, address, idBac);
        return ResponseEntity.accepted().build();
    }


    @PatchMapping("/changeStatus")
    public ResponseEntity<?> changeStatus(
            Authentication connectedUser,
            @RequestParam(name = "idBac") Integer idBac
    ){
        this.bakeryService.changeStatus(connectedUser, idBac);
        return ResponseEntity.accepted().build();
    }

    @PatchMapping("/updateDesc")
    public ResponseEntity<?> updateDesc(
            Authentication connectedUser,
            @RequestParam(name = "idBac") Integer idBac,
            @RequestBody ReqModDesc req
    ){
        this.bakeryService.modDesc(connectedUser, idBac, req);
        return ResponseEntity.accepted().build();
    }


    @PatchMapping("/updateSocial")
    public ResponseEntity<?> updateSocial(
            Authentication connectedUser,
            @RequestParam(name = "idBac") Integer idBac,
            @RequestBody UpdateSocialBakery req
    ){
        this.bakeryService.modSocial(connectedUser, idBac, req);
        return ResponseEntity.accepted().build();
    }


    // accetta ordine


    @PatchMapping("/acceptOrder")
    public ResponseEntity<?> acceptOrder(
            @RequestParam(name = "idOrder") Integer idOrder,
            Authentication connectedUser
    ){
        this.orderService.acceptOrder(connectedUser, idOrder);
        return ResponseEntity.accepted().build();
    }


    // rifiuta ordine
    @PatchMapping("/rejectOrder")
    public ResponseEntity<?> rejectOrder(
            @RequestParam(name = "idOrder") Integer idOrder,
            Authentication connectedUser
    ){
        this.orderService.rejectOrder(connectedUser, idOrder);
        return ResponseEntity.accepted().build();
    }


    // cambia stato Ordine " in Spedizione "
    @PatchMapping("/changeStatusOrder")
    public ResponseEntity<?> changeStatusOrder(
            @RequestParam(name = "idOrder") Integer idOrder,
            Authentication connectedUser
    ){
        this.orderService.changeStatus(connectedUser, idOrder);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/getAllOrdersBakery")
    public ResponseEntity<List<OrderFrontEnd>> getAllOrdersBakery(
            Authentication connectedUser
    ){
        return ResponseEntity.ok(this.bakeryService.getAllOrdersBakery(connectedUser));

    }








}

