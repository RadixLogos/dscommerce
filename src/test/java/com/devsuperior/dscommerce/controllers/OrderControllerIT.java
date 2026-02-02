package com.devsuperior.dscommerce.controllers;

import com.devsuperior.dscommerce.controllers.util.TokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TokenUtil tokenUtil;
    private Long existingOrderId,unexistingProductId;
    private String adminToken,clientToken;
    @BeforeEach
    void setUp() throws Exception{
        existingOrderId = 1L;
        unexistingProductId = 200L;
        adminToken = tokenUtil.getAccessToken(mockMvc,"alex@gmail.com","123456");
        clientToken = tokenUtil.getAccessToken(mockMvc,"maria@gmail.com","123456");
    }
    @Test
    public void findByIdShouldReturnOkAndOrderDTOWhenExistingOrderIdAndAdminUser() throws Exception{
        mockMvc.perform(get("/orders/{id}",existingOrderId)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.moment").value("2022-07-25T13:00:00Z"))
                .andExpect(jsonPath("$.status").value("PAID"))
                .andExpect(jsonPath("$.client.name").value("Maria Brown"))
                .andExpect(jsonPath("$.client.id").value(1L))
                .andExpect(jsonPath("$.payment.id").value(1L))
                .andExpect(jsonPath("$.payment.moment").value("2022-07-25T15:00:00Z"))
                .andExpect(jsonPath("$.items[0].productId").value(1L))
                .andExpect(jsonPath("$.items[0].name").value("The Lord of the Rings"))
                .andExpect(jsonPath("$.items[0].price").value(90.5))
                .andExpect(jsonPath("$.items[0].quantity").value(2))
                .andExpect(jsonPath("$.items[0].subTotal").value(181.0))
                .andExpect(jsonPath("$.items[1].productId").value(3L))
                .andExpect(jsonPath("$.items[1].name").value("Macbook Pro"))
                .andExpect(jsonPath("$.items[1].price").value(1250.0))
                .andExpect(jsonPath("$.items[1].quantity").value(1))
                .andExpect(jsonPath("$.items[1].subTotal").value(1250.0))
                .andExpect(jsonPath("$.total").value(1431.0));
    }
    @Test
    public void findByIdShouldReturnOkAndOrderDTOWhenExistingOrderIdAndClientUserAndOrderBelongToClient() throws Exception{
        mockMvc.perform(get("/orders/{id}",existingOrderId)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + clientToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.moment").value("2022-07-25T13:00:00Z"))
                .andExpect(jsonPath("$.status").value("PAID"))
                .andExpect(jsonPath("$.client.name").value("Maria Brown"))
                .andExpect(jsonPath("$.client.id").value(1L))
                .andExpect(jsonPath("$.payment.id").value(1L))
                .andExpect(jsonPath("$.payment.moment").value("2022-07-25T15:00:00Z"))
                .andExpect(jsonPath("$.items[0].productId").value(1L))
                .andExpect(jsonPath("$.items[0].name").value("The Lord of the Rings"))
                .andExpect(jsonPath("$.items[0].price").value(90.5))
                .andExpect(jsonPath("$.items[0].quantity").value(2))
                .andExpect(jsonPath("$.items[0].subTotal").value(181.0))
                .andExpect(jsonPath("$.items[1].productId").value(3L))
                .andExpect(jsonPath("$.items[1].name").value("Macbook Pro"))
                .andExpect(jsonPath("$.items[1].price").value(1250.0))
                .andExpect(jsonPath("$.items[1].quantity").value(1))
                .andExpect(jsonPath("$.items[1].subTotal").value(1250.0))
                .andExpect(jsonPath("$.total").value(1431.0));
    }

    @Test
    public void findByIdShouldReturnForbiddenWhenExistingOrderIdAndClientUserAndOrderDoesNotBelongToClient() throws Exception{
        Long notBelongingOrderId = 2L;
        mockMvc.perform(get("/orders/{id}",notBelongingOrderId)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + clientToken))
                .andExpect(status().isForbidden());
    }

    @Test
    public void findByIdShouldReturnNotFoundWhenUnexistingOrderIdAndAdminUser() throws Exception{
        mockMvc.perform(get("/orders/{id}",unexistingProductId)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findByIdShouldReturnNotFoundWhenUnexistingOrderIdAndClientUser() throws Exception{
        mockMvc.perform(get("/orders/{id}",unexistingProductId)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + clientToken))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findByIdShouldReturnUnauthorizedWhenInvalidToken() throws Exception{
        mockMvc.perform(get("/orders/{id}",existingOrderId)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + clientToken + "xyz"))
                .andExpect(status().isUnauthorized());
    }
}
