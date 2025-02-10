package it.uniromatre.breadexchange2_0.items.category;

import it.uniromatre.breadexchange2_0.bakery.Bakery;
import it.uniromatre.breadexchange2_0.bakery.BakeryMapper;
import it.uniromatre.breadexchange2_0.bakery.BakeryRepository;
import it.uniromatre.breadexchange2_0.bakery.BakeryfrontEndResponse;
import it.uniromatre.breadexchange2_0.items.item.ItemForCart;
import it.uniromatre.breadexchange2_0.items.item.ItemMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryMapper {

    private final BakeryMapper bakeryMapper;
    private final BakeryRepository bakeryRepository;
    private final ItemMapper itemMapper;

    public CategoryFrontEndResponse toCategoryFrontEndResponse(Category category){

        if(category == null) return null;

        Bakery b = bakeryRepository.findById(category.getOwner()).orElseThrow(() -> new EntityNotFoundException("bakery not found"));

        BakeryfrontEndResponse br = bakeryMapper.fromBakeryToFrontEnd(b);

        return new CategoryFrontEndResponse(
                category.getId(),
                br,
                category.getName(),
                category.getItems()
        );
    }

    public Category toCategory(String req, Bakery user){
        return Category.builder()
                .name(req)
                .owner(user.getId())
                .build();
    }


    public ListCategoryFrontEnd toListcat(List<Category> cats, Bakery b){
        return ListCategoryFrontEnd.builder()
                .name_azz(b.getName())
                .id_azz(b.getId())
                .Categorys(cats)
                .img_azz(b.getUrl_picture())
                .build();
    }

    public List<CategoryForCart> toCategoryForCartList(List<Category> categories) {
        if (categories == null || categories.isEmpty()) {
            return Collections.emptyList();
        }

        return categories.stream()
                .map(this::toCategoryForCart)
                .collect(Collectors.toList());
    }


    private CategoryForCart toCategoryForCart(Category category) {
        if (category == null) return null;


        // Trova il Bakery associato alla categoria
        Bakery bakery = bakeryRepository.findById(category.getOwner())
                .orElseThrow(() -> new EntityNotFoundException("Bakery not found"));



        // Converte la lista di Item in ItemForCart, impostando la quantità iniziale a 0
        List<ItemForCart> itemsForCart = category.getItems().stream()
                .map(itemMapper::toCart)
                .collect(Collectors.toList());


        return CategoryForCart.builder()
                .id(category.getId())
                .owner(bakery.getId())
                .name(category.getName())
                .items(itemsForCart)
                .build();
    }

}
