package it.uniromatre.breadexchange2_0.items.category;

import it.uniromatre.breadexchange2_0.bakery.Bakery;
import it.uniromatre.breadexchange2_0.bakery.BakeryMapper;
import it.uniromatre.breadexchange2_0.bakery.BakeryRepository;
import it.uniromatre.breadexchange2_0.bakery.BakeryfrontEndResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryMapper {

    private final BakeryMapper bakeryMapper;
    private final BakeryRepository bakeryRepository;

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


}
