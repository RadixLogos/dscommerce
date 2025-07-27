package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.dto.OrderDTO;
import com.devsuperior.dscommerce.dto.OrderItemDTO;
import com.devsuperior.dscommerce.entities.*;
import com.devsuperior.dscommerce.enums.OrderStatus;
import com.devsuperior.dscommerce.repositories.OrderItemRepository;
import com.devsuperior.dscommerce.repositories.OrderRepository;
import com.devsuperior.dscommerce.repositories.ProductRepository;
import com.devsuperior.dscommerce.repositories.UserRepository;
import com.devsuperior.dscommerce.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public OrderDTO findOrderById(Long id){
        var order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));
        return OrderDTO.fromOrder(order);
    }

    @Transactional
    public OrderDTO insertOrder(OrderDTO orderDTO){
        Order orderEntity = new Order();
        fromDtoToEntity(orderDTO, orderEntity);
        orderEntity = orderRepository.save(orderEntity);
        orderEntity.getItems().forEach(item ->{
            orderItemRepository.save(item);
        });
        return OrderDTO.fromOrder(orderEntity);
    }

    private void fromDtoToEntity(OrderDTO orderDTO, Order orderEntity) {
        orderEntity.setId(orderDTO.id());
        orderEntity.setMoment(Instant.now());
        orderEntity.setStatus(OrderStatus.WAITING_PAYMENT);

        User client = userRepository.getReferenceById(orderDTO.client().id());
        orderEntity.setUser(client);

        if(orderDTO.payment() != null) {
            var payment = new Payment();
            payment.setId(orderDTO.payment().id());
            payment.setMoment(orderDTO.moment());
            orderEntity.setPayment(payment);
        }
        for(OrderItemDTO item : orderDTO.items()){
            var orderItem = new OrderItem();
            var product = productRepository.getReferenceById(item.productId());
            orderItem.setProduct(product);
            orderItem.setPrice(product.getPrice());
            orderItem.setQuantity(item.quantity());
            orderItem.setOrder(orderEntity);
            orderEntity.addItem(orderItem);
        }
    }
}
