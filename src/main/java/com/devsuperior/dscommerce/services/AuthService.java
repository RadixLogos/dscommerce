package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.entities.User;
import com.devsuperior.dscommerce.services.exceptions.ForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    UserService userService;
    public void hasRoleAdminOrIsAuthorizedUser(long userId){
        User currentUser = userService.authenticated();
        if(!currentUser.hasRole("ROLE_ADMIN") && !currentUser.getId().equals(userId)){
            throw new ForbiddenException("Access denied");
        }
    }
}
