package com.devsuperior.dscommerce.dto;

import com.devsuperior.dscommerce.entities.User;

public record ClientDTO(Long id, String name) {
    public static ClientDTO fromUser(User user){
        return new ClientDTO(user.getId(),user.getName());
    }
}
