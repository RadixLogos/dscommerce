package com.devsuperior.dscommerce.dto;

import com.devsuperior.dscommerce.entities.OrderItem;

public record OrderItemDTO(
        Long productId,
        String name,
        double price,
        int quantity,
        String imgUrl
        ) {
        public static OrderItemDTO fromOrderItem(OrderItem orderItem){
                return new OrderItemDTO(
                        orderItem.getProduct().getId(),
                        orderItem.getProduct().getName(),
                        orderItem.getPrice(),
                        orderItem.getQuantity(),
                        orderItem.getImgUrl()
                        );
        }

        public double getSubTotal(){
                return price * quantity;
        }
}
