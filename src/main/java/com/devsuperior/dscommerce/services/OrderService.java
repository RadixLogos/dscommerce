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
    @Autowired
    private UserService userService;

    @Transactional(readOnly = true)
    public OrderDTO findOrderById(Long id){
        var order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));
        return OrderDTO.fromOrder(order);
    }

    @Transactional
    public OrderDTO insertOrder(OrderDTO orderDTO){
        Order orderEntity = new Order();
        orderEntity.setMoment(Instant.now());
        orderEntity.setStatus(OrderStatus.WAITING_PAYMENT);

        User client = userService.authenticated();
        orderEntity.setUser(client);

        for(OrderItemDTO item : orderDTO.items()){
            var product = productRepository.getReferenceById(item.productId());
            var orderItem = new OrderItem(orderEntity,product,item.quantity(),product.getPrice());
            orderEntity.addItem(orderItem);
        }

        orderEntity = orderRepository.save(orderEntity);
        orderItemRepository.saveAll(orderEntity.getItems());
        return OrderDTO.fromOrder(orderEntity);
    }

}
