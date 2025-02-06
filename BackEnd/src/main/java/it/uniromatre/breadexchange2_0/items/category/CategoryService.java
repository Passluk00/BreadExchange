package it.uniromatre.breadexchange2_0.items.category;

import it.uniromatre.breadexchange2_0.bakery.Bakery;
import it.uniromatre.breadexchange2_0.bakery.BakeryMapper;
import it.uniromatre.breadexchange2_0.bakery.BakeryRepository;
import it.uniromatre.breadexchange2_0.bakery.BakeryfrontEndResponse;
import it.uniromatre.breadexchange2_0.items.item.Item;
import it.uniromatre.breadexchange2_0.items.item.ItemRepository;
import it.uniromatre.breadexchange2_0.items.item.ItemService;
import it.uniromatre.breadexchange2_0.user.User;
import it.uniromatre.breadexchange2_0.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ItemService itemService;
    private final UserRepository userRepository;
    private final BakeryRepository bakeryRepository;
    private final BakeryMapper bakeryMapper;
    private final CategoryMapper categoryMapper;
    private final ItemRepository itemRepository;


    public void generaVuoto(){

        Bakery b = bakeryRepository.getBakeryById(1);

        Category c = Category.builder()
                .name("test")
                .owner(b.getId())
                .items(itemService.generaItemsVuoti())
                .build();

        categoryRepository.save(c);

    }

    public void newCategory(String req, Integer idBac, Authentication user){

        if(req == null || req.isEmpty()){
            throw new RuntimeException("request is null or non valid");
        }

        User us = (User)user.getPrincipal();
        User toCheck = userRepository.findUserById(us.getId());


        // controllo che l'utente esista
        if(toCheck==null){
            throw new EntityNotFoundException("User not Found with id: "+ ((User) user.getPrincipal()).getId());
        }

        Bakery bac = bakeryRepository.getBakeryById(idBac);

        if(bac==null){
            throw new RuntimeException("Bakery not found with id: "+idBac);
        }

        if(!bac.getOwner().equals(toCheck)){
            throw new RuntimeException("Non sei il propietario di questa bakery");
        }

        Category cat = categoryMapper.toCategory(req,bac);
        cat.setItems(null);
        categoryRepository.save(cat);

        List<Category> c = bac.getCategories();
        c.add(cat);
        bac.setCategories(c);
        bakeryRepository.save(bac);

    }


    public CategoryFrontEndResponse getCategory(Integer id) {

        Category c = categoryRepository.findByOwner(id);

        if (c == null) {
            throw new RuntimeException("Category not found");
        }

        return categoryMapper.toCategoryFrontEndResponse(c);
    }

    public ListCategoryFrontEnd getAllCategory(Integer id) {

        List<Category> c = categoryRepository.findAllByOwner(id);

        Bakery b = bakeryRepository.getBakeryById(id);

        return categoryMapper.toListcat(c,b);

    }


    @Transactional
    public void deleteCategory(Integer idCat, Authentication user){

        if(idCat == null){
            throw new RuntimeException("Category id is null");
        }

        User us = (User)user.getPrincipal();
        User toCheck = userRepository.findUserById(us.getId());

        if(toCheck==null){
            throw new RuntimeException("User not Found with id: "+ ((User) user.getPrincipal()).getId());
        }

        Category c = categoryRepository.getCategoryById(idCat);
        Bakery b = bakeryRepository.getBakeryById(c.getOwner());

        if(!toCheck.equals(b.getOwner())) {
            throw new RuntimeException("Non sei il propietario di questa bakery");
        }

        if(c == null){
            throw new RuntimeException("Category not found");
        }

        List<Item> items = c.getItems();

        List<Category> updatedCategories = b.getCategories().stream()
                .filter(category -> !category.getId().equals(idCat))
                .collect(Collectors.toList());

        b.setCategories(updatedCategories);
        bakeryRepository.save(b);
        categoryRepository.deleteById(idCat);
        itemRepository.deleteAll(items);

    }

}























