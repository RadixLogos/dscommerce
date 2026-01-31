package com.devsuperior.dscommerce.util;

import com.devsuperior.dscommerce.dto.CategoryDTO;
import com.devsuperior.dscommerce.dto.OrderDTO;
import com.devsuperior.dscommerce.dto.OrderItemDTO;
import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.entities.*;
import com.devsuperior.dscommerce.enums.OrderStatus;
import com.devsuperior.dscommerce.projections.UserDetailsProjection;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

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

    public static Product buildNewProduct(){
        var product = new Product("Head First Java","Livro sobre Java",150.0,"https://hadfrist-java-jpg.com");
        product.getCategories().add(buildCategory());
        return product;
    }

    public static ProductDTO buildProductDTO(){
        return ProductDTO.fromProduct(buildNewProduct());
    }

    public static ProductDTO buildUpdateProductDTO(){
        return new ProductDTO(null,"Head First Java 2 edição","Livro sobre Java melhorado",150.0,"https://hadfrist-java-jpg.com", List.of(CategoryDTO.fromCategory(buildCategory())));
    }
    public static ProductDTO buildNullIdProductDTO(){
        return new ProductDTO(null,"Head First Java 2 edição","Livro sobre Java melhorado",150.0,"https://hadfrist-java-jpg.com", List.of(CategoryDTO.fromCategory(buildCategory())));
    }

    public static User buildUserClient(){
        var user = new User("ana","ana@gmail.com","419782546", LocalDate.of(1999, Month.APRIL,15),"123654");
        user.setId(2L);
        user.addRole(new Role(1L,"ROLE_CLIENT"));
        return user;
    }
    public static User buildUserAdmin(){
        var user = new User("pedro","pedro@gmail.com","419786546", LocalDate.of(1999, Month.APRIL,15),"1889654");
        user.setId(3L);
        user.addRole(new Role(2L,"ROLE_ADMIN"));
        return user;
    }
    public static User buildUserCustomUserClient(String name){
        var user = new User(name,name+"@gmail.com","4190386546", LocalDate.of(1999, Month.APRIL,15),"647890866");
        user.setId(4L);
        user.addRole(new Role(1L,"ROLE_CLIENT"));
        return user;
    }


    public static UserDetailsProjection buildUserDetailsProjection(String username) {
        return new UserDetailsProjection() {
            @Override
            public String getUsername() {
                return username;
            }

            @Override
            public String getPassword() {
                return "1bef4c%verDk#234F3$";
            }

            @Override
            public Long getRoleId() {
                return 1L;
            }

            @Override
            public String getAuthority() {
                return "ROLE_CLIENT";
            }
        };
    }

    public static OrderItemDTO buildOrderItemDTO(){
        return new OrderItemDTO(1L,null,25.50,5,null);
    }
    public static Order buildOrder(){
        var order = new Order(Instant.now(), OrderStatus.PAID);
        var orderItem = new OrderItem(order,buildProduct(),2,25.5,"https://image.com");
        order.setId(1L);
        order.setUser(buildUserClient());
        order.addItem(orderItem);
        return order;
    }

    public static Order buildWatingPaymentOrder(){
        var order = new Order(Instant.now(), OrderStatus.WAITING_PAYMENT);
        var orderItem = new OrderItem(order,buildProduct(),2,25.5,"https://image.com");
        order.setId(2L);
        order.setUser(buildUserClient());
        order.addItem(orderItem);
        return order;
    }

    public static OrderDTO buildNewOrderDTO(){
        return new OrderDTO(null,null,null,null,null,List.of(buildOrderItemDTO()));
    }

}
