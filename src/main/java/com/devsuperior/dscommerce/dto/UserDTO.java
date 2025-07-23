package com.devsuperior.dscommerce.dto;

import com.devsuperior.dscommerce.entities.User;
import jakarta.persistence.Column;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public record UserDTO(Long id,String name, String email, String phone, LocalDate birthDate, List<String> roles) {
    public static UserDTO fromUser(User user){
        List<String> roles = new ArrayList<>();
        for(GrantedAuthority role : user.getAuthorities()){
            roles.add(role.getAuthority());
        }
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getBirthDate(),
                roles
                );
    }
}
