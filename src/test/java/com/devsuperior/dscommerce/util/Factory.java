package com.devsuperior.dscommerce.util;

import com.devsuperior.dscommerce.entities.Category;
import com.devsuperior.dscommerce.entities.Product;

public class Factory {
    public static Category buildCategory(){
       var category = new Category("Livros");
       category.setId(1L);
        return category;
    }

    public static Product buildProduct(){
        var product = new Product("Luz sobre a idade média","Livro sobre a idade média",150.0,"https://luz-sobre-idade-media-regina-jpg.com");
        product.getCategories().add(buildCategory());
        return product;
    }

}
