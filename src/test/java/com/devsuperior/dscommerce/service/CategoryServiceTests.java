package com.devsuperior.dscommerce.service;

import com.devsuperior.dscommerce.dto.CategoryDTO;
import com.devsuperior.dscommerce.entities.Category;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.repositories.CategoryRepository;
import com.devsuperior.dscommerce.repositories.ProductRepository;
import com.devsuperior.dscommerce.services.CategoryService;
import com.devsuperior.dscommerce.services.ProductService;
import com.devsuperior.dscommerce.util.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CategoryServiceTests {
    @InjectMocks
    private CategoryService categoryService;
    @Mock
    private CategoryRepository categoryRepository;

    private Category category;
    private List<Category> categories;
    @BeforeEach
    void setUp(){
        category = Factory.buildCategory();
        categories = new ArrayList<>();
        categories.add(category);
        when(categoryRepository.findAll()).thenReturn(categories);
    }

    @Test
    public void findAllShouldRetornListOfCategoryDTO(){
        var response = categoryService.findAll();

        Assertions.assertEquals(1L,response.getFirst().id());
        Assertions.assertEquals(category.getName(),response.getFirst().name());
        Assertions.assertEquals(CategoryDTO.class,response.getFirst().getClass());

    }
}
