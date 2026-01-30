package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.repositories.ProductRepository;
import com.devsuperior.dscommerce.services.exceptions.DatabaseException;
import com.devsuperior.dscommerce.services.exceptions.ResourceNotFoundException;
import com.devsuperior.dscommerce.util.Factory;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {
    @InjectMocks
    private ProductService productService;
    @Mock
    private ProductRepository productRepository;

    private Long existingId;
    private Long unexistingId;
    private Long dependentId;
    private String existingProductName;
    private Product product;
    private Product newProduct;
    private ProductDTO productDTO;
    private ProductDTO updateProductDTO;
    PageImpl<Product> page;
    @BeforeEach
    void setup(){
        existingId=1L;
        dependentId = 2L;
        unexistingId=20000L;
        existingProductName="Luz sobre a idade média";
        product = Factory.buildProduct();
        newProduct = Factory.buildNewProduct();
        productDTO = Factory.buildProductDTO();
        updateProductDTO = Factory.buildNullIdProductDTO();
        page = new PageImpl<>(List.of(product));
        when(productRepository.findById(existingId)).thenReturn(Optional.of(product));
        when(productRepository.getReferenceById(existingId)).thenReturn(newProduct);
        when(productRepository.getReferenceById(unexistingId)).thenThrow(EntityNotFoundException.class);
        when(productRepository.findById(existingId)).thenReturn(Optional.of(product));
        when(productRepository.findById(unexistingId)).thenReturn(Optional.empty()).thenThrow(ResourceNotFoundException.class);
        when(productRepository.findByName(eq(existingProductName),any())).thenReturn(page);
        when(productRepository.save(newProduct)).thenReturn(newProduct);
        doReturn(true).when(productRepository).existsById(existingId);
        doReturn(true).when(productRepository).existsById(dependentId);
        doReturn(false).when(productRepository).existsById(unexistingId);
        doNothing().when(productRepository).deleteById(existingId);
        doThrow(DataIntegrityViolationException.class).when(productRepository).deleteById(dependentId);

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
    public void findAllProductsShouldReturnPageProductMinDTOWhenExistingName(){
        Pageable pageable = PageRequest.of(0,12);
        var response = productService.findAllProducts(pageable,existingProductName);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1,response.getTotalElements());
        Assertions.assertEquals(1,response.getSize());
        Assertions.assertEquals(product.getId(),response.getContent().getFirst().id());
        Assertions.assertEquals(product.getPrice(),response.getContent().getFirst().price());
        Assertions.assertEquals(product.getImgUrl(),response.getContent().getFirst().imgUrl());
    }

    @Test
    public void insertProductShouldReturnProductDTOWhenValidData(){
        var response = productService.insertProduct(productDTO);
        Assertions.assertEquals(newProduct.getId(),response.id());
        Assertions.assertEquals(newProduct.getName(),response.name());
        Assertions.assertEquals(newProduct.getDescription(),response.description());
        Assertions.assertEquals(newProduct.getPrice(),response.price());
        Assertions.assertEquals(newProduct.getImgUrl(),response.imgUrl());
    }

    @Test
    public void updateProductShouldReturnUpdatedProductDTOWhenValidData(){
        var response = productService.updateProduct(1L,updateProductDTO);
        Assertions.assertEquals(newProduct.getId(),response.id());
        Assertions.assertEquals(newProduct.getName(),response.name());
        Assertions.assertEquals(newProduct.getDescription(),response.description());
        Assertions.assertEquals(newProduct.getPrice(),response.price());
        Assertions.assertEquals(newProduct.getImgUrl(),response.imgUrl());
    }

    @Test
    public void updateProductShouldThrowResourceNotFoundExceptionWhenUnexistingId(){
        Assertions.assertThrows(ResourceNotFoundException.class,()->{
            productService.updateProduct(unexistingId,updateProductDTO);
        });
    }

    @Test
    public void deleteProductShouldThrowResourceNotFoundExceptionWhenUnexistingId(){
        Assertions.assertThrows(ResourceNotFoundException.class,()->{
            productService.deleteProduct(unexistingId);
        });
    }


    @Test
    public void deleteProductShouldDoNothingWhenExistingId(){
        Assertions.assertDoesNotThrow(()->{
            productService.deleteProduct(existingId);
        });
        verify(productRepository,times(1)).deleteById(existingId);
    }

    @Test
    public void deleteProductShouldThrowDataIntegrityViolationExceptionWhenDependingId(){
        Assertions.assertThrows(DatabaseException.class,()->{
            productService.deleteProduct(dependentId);
        });
        verify(productRepository,times(1)).deleteById(dependentId);
    }

}
