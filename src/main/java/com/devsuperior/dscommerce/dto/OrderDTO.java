package com.devsuperior.dscommerce.dto;

import com.devsuperior.dscommerce.entities.Order;
import com.devsuperior.dscommerce.enums.OrderStatus;
import jakarta.validation.constraints.NotEmpty;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public record OrderDTO(
        Long id,
        Instant moment,
        OrderStatus status,
        ClientDTO client,
        PaymentDTO payment,
        @NotEmpty(message = "O pedido deve conter ao menos um item")
        List<OrderItemDTO> items
) {
    public static OrderDTO fromOrder(Order order){
        ClientDTO client = ClientDTO.fromUser(order.getUser());
        PaymentDTO payment = null;
        if(order.getPayment() != null)
            payment = PaymentDTO.fromPayment(order.getPayment());
        List<OrderItemDTO> items = new ArrayList<>();
        order.getItems().forEach(oI -> {
            OrderItemDTO item = OrderItemDTO.fromOrderItem(oI);
            items.add(item);
        });
        return new OrderDTO(order.getId(),order.getMoment(),order.getStatus(),client, payment,items);
    }

    public double getTotal(){
        double total = 0;
        for(OrderItemDTO itemDTO : items){
            total+= itemDTO.getSubTotal();
        }
        return total;
    }
}
