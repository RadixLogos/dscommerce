package com.devsuperior.dscommerce.service;

import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.repositories.ProductRepository;
import com.devsuperior.dscommerce.services.ProductService;
import com.devsuperior.dscommerce.services.exceptions.ResourceNotFoundException;
import com.devsuperior.dscommerce.util.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {
    @InjectMocks
    private ProductService productService;
    @Mock
    private ProductRepository productRepository;

    private Long existingId;
    private Long unexistingId;
    private String existingProductName;
    private String unexistingProductName;
    private Product product;
    private List<Product> products;
    @BeforeEach
    void setup(){
        existingId=1L;
        unexistingId=2L;
        existingProductName="Luz sobre a idade média";
        unexistingProductName = "Pc gamer";
        product = Factory.buildProduct();
        products = new ArrayList<>();
        products.add(product);
        Page<Product> pagedProducts = new PageImpl<>(products, Pageable.ofSize(12),products.size());
        when(productRepository.findById(existingId)).thenReturn(Optional.of(product));
        when(productRepository.findById(unexistingId)).thenReturn(Optional.empty()).thenThrow(ResourceNotFoundException.class);
        when(productRepository.findByName(eq(existingProductName),any())).thenReturn(pagedProducts);
    }

   @Test
    public void findProductByIdShouldReturnProductDTOWhenExistingId(){
        var response = productService.findProductById(existingId);
       Assertions.assertEquals(product.getId(),response.id());
       Assertions.assertEquals(product.getName(),response.name());
       Assertions.assertEquals(product.getDescription(),response.description());
       Assertions.assertEquals(product.getPrice(),response.price());
       Assertions.assertEquals(product.getImgUrl(),response.imgUrl());
   }

   @Test
    public void findProductByIdShouldThrowResourceNotFoundExceptionWhenUnexistingId(){
        Assertions.assertThrows(ResourceNotFoundException.class,()->{
            productService.findProductById(unexistingId);
        });
    }

    @Test
    public void findAllProductsShouldReturnPageProductDTOWhenExistingName(){
        var response = productService.findAllProducts(Pageable.unpaged(),existingProductName);
        Assertions.assertEquals(products.size(),response.getTotalElements());
        Assertions.assertEquals(products.getFirst().getId(),response.getContent().getFirst().id());
        Assertions.assertEquals(products.getFirst().getPrice(),response.getContent().getFirst().price());
        Assertions.assertEquals(products.getFirst().getImgUrl(),response.getContent().getFirst().imgUrl());
    }
}
