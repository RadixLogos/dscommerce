package com.devsuperior.dscommerce.controllers;

import com.devsuperior.dscommerce.controllers.util.TokenUtil;
import com.devsuperior.dscommerce.dto.CategoryDTO;
import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.services.ProductService;
import com.devsuperior.dscommerce.util.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductControllerIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TokenUtil tokenUtil;

    private String productName;
    private String adminToken;
    private String clientToken;
    private ProductDTO validProduct;
    @BeforeEach
    void setUp()throws Exception{
        productName = "Macbook";
        validProduct = Factory.buildNullIdProductDTO();

        adminToken = tokenUtil.getAccessToken(mockMvc,"alex@gmail.com","123456");
        clientToken = tokenUtil.getAccessToken(mockMvc,"maria@gmail.com","123456");
    }

    @Test
    public void findAllProductsShouldReturnOrganizedPageWhenSearchedByName() throws Exception{
        ResultActions result =
                mockMvc.perform(get("/products?name={productName}",productName)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content").exists());
        result.andExpect(jsonPath("$.content[0].id").value("3"));
        result.andExpect(jsonPath("$.content[0].name").value("Macbook Pro"));
        result.andExpect(jsonPath("$.content[0].price").value("1250.0"));
        result.andExpect(jsonPath("$.content[0].imgUrl").value("https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/3-big.jpg"));

    }
    @Test
    public void insertProductShouldReturnProductDTOWhenAdminUserAndValidData() throws Exception{
        String jsonBody = objectMapper.writeValueAsString(validProduct);
        mockMvc.perform(post("/products")
                .header("Authorization","Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(validProduct.name()))
                .andExpect(jsonPath("$.price").value(validProduct.price()))
                .andExpect(jsonPath("$.description").value(validProduct.description()))
                .andExpect(jsonPath("$.imgUrl").value(validProduct.imgUrl()))
                .andExpect(jsonPath("$.categories[0].id").value(validProduct.categories().getFirst().id()));
    }

    @Test
    public void insertProductShouldReturnStatus422WithErrorMessageWhenAdminUserAndInvalidProductName() throws Exception{
        var invalidProduct = new ProductDTO(null,"He","Livro melhorado sobre Java",150.0,"https://hadfrist-java-jpg.com", List.of(CategoryDTO.fromCategory(Factory.buildCategory())));
        String jsonBody = objectMapper.writeValueAsString(invalidProduct);
        mockMvc.perform(post("/products")
                        .header("Authorization","Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.error").value("Dados inválidos"))
                .andExpect(jsonPath("$.errors[0].message").value("Nome precisa ter entre 3 a 80 caracteres"))
                .andExpect(jsonPath("$.errors[0].field").value("name"));

    }

    @Test
    public void insertProductShouldReturnStatus422WithErrorMessageWhenAdminUserAndInvalidDescription() throws Exception{
        var invalidProduct = new ProductDTO(null,"Head First Java","Liv",150.0,"https://hadfrist-java-jpg.com", List.of(CategoryDTO.fromCategory(Factory.buildCategory())));
        String jsonBody = objectMapper.writeValueAsString(invalidProduct);
        mockMvc.perform(post("/products")
                        .header("Authorization","Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.error").value("Dados inválidos"))
                .andExpect(jsonPath("$.errors[0].message").value("Descrição deve ter no mínimo 10 caracteres"))
                .andExpect(jsonPath("$.errors[0].field").value("description"));

    }

    @Test
    public void insertProductShouldReturnStatus422WithErrorMessageWhenAdminUserAndInvalidPrice() throws Exception{
        var invalidProduct = new ProductDTO(null,"Head First Java","Livro melhorado sobre Java",0.0,"https://hadfrist-java-jpg.com", List.of(CategoryDTO.fromCategory(Factory.buildCategory())));
        String jsonBody = objectMapper.writeValueAsString(invalidProduct);
        mockMvc.perform(post("/products")
                        .header("Authorization","Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.error").value("Dados inválidos"))
                .andExpect(jsonPath("$.errors[0].message").value("O preço deve ser um número positivo"))
                .andExpect(jsonPath("$.errors[0].field").value("price"));

    }

    @Test
    public void insertProductShouldReturnStatus422WithErrorMessageWhenAdminUserAndNoCategory() throws Exception{
        var invalidProduct = new ProductDTO(null,"Head First Java","Livro melhorado sobre Java",100.0,"https://hadfrist-java-jpg.com", null);
        String jsonBody = objectMapper.writeValueAsString(invalidProduct);
        mockMvc.perform(post("/products")
                        .header("Authorization","Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.error").value("Dados inválidos"))
                .andExpect(jsonPath("$.errors[0].message").value("O produto deve ter ao menos uma categoria"))
                .andExpect(jsonPath("$.errors[0].field").value("categories"));

    }
    @Test
    public void insertProductShouldReturnStatus403WhenClientUser() throws Exception{
        String jsonBody = objectMapper.writeValueAsString(validProduct);
        mockMvc.perform(post("/products")
                        .header("Authorization","Bearer " + clientToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

    }

    @Test
    public void insertProductShouldReturnStatus401WhenNotAdminOrClient() throws Exception{
        String jsonBody = objectMapper.writeValueAsString(validProduct);
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

    }
}
