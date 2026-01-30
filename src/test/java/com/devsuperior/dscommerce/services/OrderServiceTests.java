package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.dto.OrderDTO;
import com.devsuperior.dscommerce.entities.Order;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.entities.User;
import com.devsuperior.dscommerce.repositories.OrderItemRepository;
import com.devsuperior.dscommerce.repositories.OrderRepository;
import com.devsuperior.dscommerce.repositories.ProductRepository;
import com.devsuperior.dscommerce.services.exceptions.ForbiddenException;
import com.devsuperior.dscommerce.services.exceptions.ResourceNotFoundException;
import com.devsuperior.dscommerce.util.Factory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    @Mock
    private ProductRepository productRepository;

    private Order order;
    private User authorizedUser;
    private User unauthorizedUser;
    private Long existingOrderId;
    private Long unexistingOrderId;
    private Long existingProductId;
    private Product product;
    private OrderDTO orderDTO;
    private  Order newOrder;
    private OrderDTO newOrderDTO;

    @BeforeEach
    void setUp ()throws Exception{
        order = Factory.buildOrder();
        newOrder = Factory.buildWatingPaymentOrder();
        orderDTO = OrderDTO.fromOrder(order);
        newOrderDTO = Factory.buildNewOrderDTO();
        product = Factory.buildProduct();
        authorizedUser = Factory.buildUserClient();
        unauthorizedUser = Factory.buildUserCustomUserClient("Pedro");
        existingOrderId =1L;
        unexistingOrderId = 200L;
        existingProductId = 1L;
        when(orderRepository.findById(existingOrderId)).thenReturn(Optional.of(order));
        when(orderRepository.findById(unexistingOrderId)).thenReturn(Optional.empty()).thenThrow(ResourceNotFoundException.class);
        doReturn(authorizedUser).when(userService).authenticated();
        when(productRepository.getReferenceById(existingProductId)).thenReturn(product);
        when(orderRepository.save(any())).thenReturn(newOrder);
    }

    @Test
    public void findOrderByIdShouldReturnOrderDTOWhenExistingIdAndAuthorizedUser(){
        var response = orderService.findOrderById(order.getId());
        assertNotNull(response);
        assertEquals(orderDTO.id(),response.id());
        assertEquals(orderDTO.status(),response.status());
        assertEquals(orderDTO.moment(),response.moment());
        assertEquals(orderDTO.getTotal(),response.getTotal());

    }
    @Test
    public void findOrderByIdShouldThrowResourceNotFoundExceptionWhenUnexistingId(){
        assertThrows(ResourceNotFoundException.class,()->{
            orderService.findOrderById(unexistingOrderId);
        });
    }
    @Test
    public void findOrderByIdShouldThrowForbiddenExceptionWhenExistingIdAndUnauthorizedUser(){
       Mockito.doThrow(ForbiddenException.class).when(authService).hasRoleAdminOrIsAuthorizedUser(any());
        assertThrows(ForbiddenException.class,()->{
            orderService.findOrderById(existingOrderId);
        });
    }
    @Test
    public void insertOrderShouldReturnOrderDTOWhenExistingProduct(){
        var response = orderService.insertOrder(newOrderDTO);
        assertNotNull(response);
        assertEquals(2L,response.id());
        assertEquals(newOrder.getProducts().getFirst().getName(),response.items().getFirst().name());
        assertEquals(newOrder.getProducts().getFirst().getId(),response.items().getFirst().productId());

    }
}
