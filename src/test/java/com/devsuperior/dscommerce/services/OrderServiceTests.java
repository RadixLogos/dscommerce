package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.entities.OrderItem;
import com.devsuperior.dscommerce.repositories.OrderItemRepository;
import com.devsuperior.dscommerce.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class OrderServiceTests {

    @InjectMocks
    private OrderService orderService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private AuthService authService;
    @Mock
    private UserService userService;
    @Mock
    private OrderItemRepository orderItemRepository;

    private Order order;

    @BeforeEach
    void setUp ()throws Exception{

    }
}
