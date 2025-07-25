package com.devsuperior.dscommerce.dto;

import com.devsuperior.dscommerce.entities.Category;

public record CategoryDTO(Long id, String name) {
    public static CategoryDTO fromCategory(Category c){
        return new CategoryDTO(c.getId(),c.getName());
    }
}
