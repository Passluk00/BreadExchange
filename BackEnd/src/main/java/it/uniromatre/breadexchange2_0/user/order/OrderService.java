package it.uniromatre.breadexchange2_0.user.order;

import it.uniromatre.breadexchange2_0.bakery.Bakery;
import it.uniromatre.breadexchange2_0.bakery.BakeryRepository;
import it.uniromatre.breadexchange2_0.user.ItemsCart.ItemsCart;
import it.uniromatre.breadexchange2_0.user.User;
import it.uniromatre.breadexchange2_0.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {



    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final BakeryRepository bakeryRepository;


    @Transactional
    public void sendOrder(Authentication connectedUser) {

        if (connectedUser == null) {
            throw new RuntimeException("Invalid parameters");
        }

        User user = (User) connectedUser.getPrincipal();
        User toCheck = userRepository.findUserById(user.getId());

        if (toCheck == null) {
            throw new EntityNotFoundException("User not found");
        }

        if (toCheck.getCart() == null || toCheck.getCart().getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // Prendi gli item nel carrello
        List<ItemsCart> cartItems = toCheck.getCart().getItems()
                .stream()
                .map(item -> new ItemsCart(item))
                .collect(Collectors.toList());

        // Prendi la bakery corrispondente
        Bakery bakery = bakeryRepository.getBakeryById(toCheck.getCart().getIdBac());
        if (bakery == null) {
            throw new EntityNotFoundException("Bakery not found");
        }

        // Creazione dell'ordine
        Order order = Order.builder()
                .user(toCheck)
                .bakery(bakery)
                .address(toCheck.getAddress())
                .items(cartItems)
                .status("In Lavorazione")
                .build();

        order.calculateTotalPrice();
        orderRepository.save(order);

        // Pulizia del carrello
        toCheck.getCart().getItems().clear();
        toCheck.getCart().setIdBac(null);
        toCheck.getCart().setTotal(new BigDecimal(0));

        // Aggiunta dell'ordine a User e Bakery
        toCheck.getOrders().add(order);
        bakery.getOrders().add(order);

        // Salvataggio degli aggiornamenti
        userRepository.save(toCheck);
        bakeryRepository.save(bakery);
    }




    // regect order

    public void rejectOrder(Authentication connectedUser, Integer orderId) {

        if(connectedUser==null || orderId==null){
            throw new RuntimeException("Invalid parameters");
        }

        User user = (User) connectedUser.getPrincipal();
        User toCheck = userRepository.findUserById(user.getId());

        if(toCheck==null){
            throw new EntityNotFoundException("User not found");
        }

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException("Order not found"));



        Bakery bac = order.getBakery();

        /* TODO vecchio
        Bakery bac = bakeryRepository.getBakeryById(order.getIdBac());
         */

        if(bac==null){
            throw new EntityNotFoundException("Bakery not found");
        }

        if(!bac.getOwner().getId().equals(toCheck.getId())){
            throw new EntityNotFoundException("non sei il propietario");
        }


        order.setAccepted(false);
        order.setStatus("Rifiutato");
        orderRepository.save(order);
    }


    // acept Order

    public void acceptOrder(Authentication connectedUser, Integer orderId) {

        if(connectedUser==null || orderId==null){
            throw new RuntimeException("Invalid parameters");
        }

        User user = (User) connectedUser.getPrincipal();
        User toCheck = userRepository.findUserById(user.getId());

        if(toCheck==null){
            throw new EntityNotFoundException("User not found");
        }

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException("Order not found"));


        Bakery bac = order.getBakery();

        /* TODO vecchio
        Bakery bac = bakeryRepository.getBakeryById(order.getIdBac());
         */

        if(bac==null){
            throw new EntityNotFoundException("Bakery not found");
        }

        if(!bac.getOwner().getId().equals(toCheck.getId())){
            throw new EntityNotFoundException("non sei il propietario");
        }

        order.setAccepted(true);
        order.setStatus("Accettato");
        orderRepository.save(order);
    }




    // change status

    public void changeStatus(Authentication connectedUser, Integer orderId) {

        if(connectedUser==null || orderId==null){
            throw new RuntimeException("Invalid parameters");
        }

        User user = (User) connectedUser.getPrincipal();
        User toCheck = userRepository.findUserById(user.getId());

        if(toCheck==null){
            throw new EntityNotFoundException("User not found");
        }

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException("Order not found"));

        Bakery bac = order.getBakery();

        /* TODO vecchio
        Bakery bac = bakeryRepository.getBakeryById(order.getIdBac());
         */

        if(bac==null){
            throw new EntityNotFoundException("Bakery not found");
        }

        if(!bac.getOwner().getId().equals(toCheck.getId())){
            throw new EntityNotFoundException("non sei il propietario");
        }

        order.setStatus("Spedito");
        orderRepository.save(order);
    }





}
