package it.uniromatre.breadexchange2_0.items.item;

import it.uniromatre.breadexchange2_0.bakery.Bakery;
import it.uniromatre.breadexchange2_0.bakery.BakeryRepository;
import it.uniromatre.breadexchange2_0.file.FileStorageService;
import it.uniromatre.breadexchange2_0.items.category.Category;
import it.uniromatre.breadexchange2_0.items.category.CategoryRepository;
import it.uniromatre.breadexchange2_0.items.category.CategoryService;
import it.uniromatre.breadexchange2_0.role.Role;
import it.uniromatre.breadexchange2_0.user.User;
import it.uniromatre.breadexchange2_0.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.console;
import static java.lang.System.in;

@Service
@AllArgsConstructor
public class ItemService {

    private static final Logger log = LoggerFactory.getLogger(ItemService.class);
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BakeryRepository bakeryRepository;
    private final ItemMapper itemMapper;
    private final CategoryRepository categoryRepository;
    private final FileStorageService fileStorageService;


    public List<Item> generaItemsVuoti(){

        Item a = Item.builder()
                .name("test1")
                .description("desc1")
                .price(new BigDecimal("19.90"))
                .img(null)
                .build();

        Item b = Item.builder()
                .name("test2")
                .description("desc2")
                .price(new BigDecimal("18.90"))
                .img(null)
                .build();

        Item c = Item.builder()
                .name("test3")
                .description("desc3")
                .price(new BigDecimal("17.90"))
                .img(null)
                .build();

        List<Item> items = new ArrayList<>();
        items.add(a);
        items.add(b);
        items.add(c);

        itemRepository.save(a);
        itemRepository.save(b);
        itemRepository.save(c);


        return items;
    }


    public void newItem(ItemRequest req, Authentication user, Integer idBak, Integer idCat ,MultipartFile file){

        if(req == null || req.getName().isEmpty() || req.getDescription().isEmpty()){
            throw new RuntimeException("Request is not Valid");
        }

        User us = (User)user.getPrincipal();
        User toCheck = userRepository.findUserById(us.getId());


        // controllo che l'utente esista
        if(toCheck==null){
            throw new EntityNotFoundException("User not Found with id: "+ ((User) user.getPrincipal()).getId());
        }

        Bakery bac = bakeryRepository.getBakeryById(idBak);

        if(bac==null){
            throw new RuntimeException("Bakery not found with id: "+idBak);
        }

        if(!bac.getOwner().getId().equals(toCheck.getId())){
            throw new RuntimeException("Non sei il propietario di questa bakery");
        }

        Category cat = categoryRepository.getCategoryById(idCat);
        if(cat == null){
            throw new RuntimeException("Category not found with id: "+idCat);
        }


        Item nuovo = itemMapper.fromRequestToItem(req);
        if(file != null){
            nuovo.setImg(fileStorageService.saveFile(file,toCheck.getId()));
        }
        itemRepository.save(nuovo);
        this.addToCategory(nuovo,cat);

    }

    // funzione interna
    private void addToCategory(Item nuovo, Category category) {

        // non serve che controllo validita input perche gia controllate

        List<Item> lista = category.getItems();
        lista.add(nuovo);
        category.setItems(lista);
        categoryRepository.save(category);
    }

    public void deleteItem(Integer idItem, Authentication user, Integer idBak, Integer idCat){

        User us = (User)user.getPrincipal();
        User toCheck = userRepository.findUserById(us.getId());


        // controllo che l'utente esista
        if(toCheck==null){
            throw new EntityNotFoundException("User not Found with id: "+ ((User) user.getPrincipal()).getId());
        }

        Bakery bac = bakeryRepository.getBakeryById(idBak);

        if(bac==null){
            throw new RuntimeException("Bakery not found with id: "+idBak);
        }

        if(!bac.getOwner().equals(toCheck)){
            throw new RuntimeException("Non sei il propietario di questa bakery");
        }


        if(idItem==null){
            throw new RuntimeException("idItem is null");
        }

        Category cat = categoryRepository.getCategoryById(idCat);

        if(cat == null){
            throw new RuntimeException("Category not found with id: "+ idCat);
        }

        cat.getItems().removeIf(item -> item.getId().equals(idItem));

        categoryRepository.save(cat);
        itemRepository.deleteById(idItem);

        // modifica categori in cui è salvato

    }

    public void modificaItem(Integer idBak, Integer idItem, ItemRequest req, Authentication user){


        if(req == null){
            throw new RuntimeException("request is null");
        }

        User us = (User)user.getPrincipal();
        User toCheck = userRepository.findUserById(us.getId());


        // controllo che l'utente esista
        if(toCheck==null){
            throw new EntityNotFoundException("User not Found with id: "+ ((User) user.getPrincipal()).getId());
        }

        Bakery bac = bakeryRepository.getBakeryById(idBak);

        if(bac==null){
            throw new RuntimeException("Bakery not found with id: "+idBak);
        }

        if(!bac.getOwner().equals(toCheck)){
            throw new RuntimeException("Non sei il propietario di questa bakery");
        }

        if(idItem==null){
            throw new RuntimeException("idItem is null");
        }

        Item toMod = itemRepository.getItemById(idItem);

        if(toMod==null){
            throw new EntityNotFoundException("Item not Found with id: "+idItem);
        }


        toMod.setName(req.getName());
        toMod.setDescription(req.getDescription());
        toMod.setPrice(req.getPrice());

        itemRepository.save(toMod);
    }





    public void modItem(Integer idBac, Integer idItem, ItemRequest req, Authentication user){


        if(req == null || idBac == null || idItem == null){
            throw new RuntimeException("request data is invalid");
        }

        User us = (User)user.getPrincipal();
        User toCheck = userRepository.findUserById(us.getId());

        if(toCheck==null){
            throw new RuntimeException("User not Found with id: "+ ((User) user.getPrincipal()).getId());
        }

        Bakery bac = bakeryRepository.getBakeryById(idBac);

        if(bac==null){
            throw new RuntimeException("Bakery not found with id: "+idBac);
        }

        Item daMod = itemRepository.getItemById(idItem);

        if(daMod==null){
            throw new RuntimeException("Item not Found with id: "+idItem);
        }

        if(req.getName() != null && req.getDescription() != null && req.getPrice() != null){
            daMod.setName(req.getName());
            daMod.setDescription(req.getDescription());
            daMod.setPrice(req.getPrice());
        }

        log.error("dati img: "+ daMod.getImg());

        itemRepository.save(daMod);










    }




}
