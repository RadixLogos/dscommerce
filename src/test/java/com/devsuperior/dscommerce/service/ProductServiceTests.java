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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {
    @InjectMocks
    private ProductService productService;
    @Mock
    private ProductRepository productRepository;

    private Long existingId;
    private Long unexistingId;
    private Product product;
    @BeforeEach
    void setup(){
        existingId=1L;
        unexistingId=2L;
        product = Factory.buildProduct();

        when(productRepository.findById(existingId)).thenReturn(Optional.of(product));
        when(productRepository.findById(unexistingId)).thenReturn(Optional.empty()).thenThrow(ResourceNotFoundException.class);
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
}
