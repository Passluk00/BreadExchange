package it.uniromatre.breadexchange2_0.user.order;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderMapper {

    public OrderFrontEnd toFrontEnd(Order order) {

        return OrderFrontEnd.builder()
                .id(order.getId())
                .idBac(order.getBakery().getId())
                .nameAzz(order.getBakery().getName())
                .address(order.getAddress())
                .items(order.getItems())
                .accepted(order.isAccepted())
                .status(order.getStatus())
                .TotalPrice(order.getTotalPrice())
                .build();
    }

    public List<OrderFrontEnd> toFrontEnd(List<Order> orders) {

        if (orders == null || orders.isEmpty()) {
            throw new IllegalArgumentException("List of orders is null or empty");
        }

        return orders.stream()
                .map(this::toFrontEnd)
                .collect(Collectors.toList());
    }


}
