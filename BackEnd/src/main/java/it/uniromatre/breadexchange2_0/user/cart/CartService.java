package it.uniromatre.breadexchange2_0.user.cart;

import it.uniromatre.breadexchange2_0.items.item.Item;
import it.uniromatre.breadexchange2_0.items.item.ItemRepository;
import it.uniromatre.breadexchange2_0.user.ItemsCart.ItemsCart;
import it.uniromatre.breadexchange2_0.user.ItemsCart.ItemsCartRepository;
import it.uniromatre.breadexchange2_0.user.User;
import it.uniromatre.breadexchange2_0.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class CartService {


    private final ItemRepository itemRepository;
    private final ItemsCartRepository itemsCartRepository;
    private final UserRepository userRepository;




    // Aggiungi Oggetto

    public void addToCart(Integer idItem, Integer qnt, Integer idBac , Authentication connectedUser) {

        if(idItem==null || qnt==null || connectedUser==null || idBac==null){
            throw new RuntimeException("Invalid parameters");
        }

        User user = (User) connectedUser.getPrincipal();

        User toCheck = userRepository.findUserById(user.getId());
        if(toCheck==null){
            throw new EntityNotFoundException("User not found");
        }

        Item it = itemRepository.getItemById(idItem);

        if(it==null){
            throw new EntityNotFoundException("Item not found");
        }

        ItemsCart toAdd = new ItemsCart();
        toAdd.setDetails(it);
        toAdd.setQuantity(qnt);

        if(toCheck.getCart().getIdBac() == null){
            toCheck.getCart().setIdBac(idBac);
        }

        itemsCartRepository.save(toAdd);



        List<ItemsCart> list = toCheck.getCart().getItems();
        list.add(toAdd);
        toCheck.getCart().setItems(list);
        toCheck.getCart().updateTotal();
        userRepository.save(toCheck);


    }


    // Rimuovi Oggetto

    public void removeToCart(Integer idItem, Authentication connectedUser) {

        if(idItem==null || connectedUser==null){
            throw new RuntimeException("Invalid parameters");
        }

        User user = (User) connectedUser.getPrincipal();
        User toCheck = userRepository.findUserById(user.getId());

        if(toCheck==null){
            throw new EntityNotFoundException("User not found");
        }

        toCheck.getCart().getItems().removeIf(f -> Objects.equals(f.getId(), idItem));

        if(toCheck.getCart().getItems().isEmpty()){
            toCheck.getCart().setIdBac(null);
        }

        toCheck.getCart().updateTotal();
        userRepository.save(user);
        itemsCartRepository.deleteById(idItem);

    }


    // modifica quantita

    public void modQuantity(Integer idItemCart, Integer qnt, Authentication connectedUser) {


        if(idItemCart==null || connectedUser==null){
            throw new RuntimeException("Invalid parameters");
        }


        User user = (User) connectedUser.getPrincipal();
        User toCheck = userRepository.findUserById(user.getId());


        if(toCheck==null){
            throw new EntityNotFoundException("User not found");
        }


        List<ItemsCart> list = toCheck.getCart().getItems();




        for( ItemsCart elem : list ){
            if(elem.getId().equals(idItemCart)){
                elem.setQuantity(qnt);
                itemsCartRepository.save(elem);
            }
        }


        toCheck.getCart().setItems(list);
        toCheck.getCart().updateTotal();
        userRepository.save(toCheck);

    }












}
