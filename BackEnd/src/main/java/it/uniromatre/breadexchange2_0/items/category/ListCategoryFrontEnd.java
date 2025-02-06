package it.uniromatre.breadexchange2_0.items.category;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ListCategoryFrontEnd {

    String name_azz;

    Integer id_azz;

    List<Category> Categorys;

    String img_azz;

}
