package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.entities.User;
import com.devsuperior.dscommerce.services.exceptions.ForbiddenException;
import com.devsuperior.dscommerce.util.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class AuthServiceTests {

    @Mock
    private UserService userService;
    @InjectMocks
    private AuthService authService;
    private User adminUser,selfUser,otherUser;
    @BeforeEach
    void setUp(){
        adminUser = Factory.buildUserAdmin();
        selfUser = Factory.buildUserClient();
        otherUser = Factory.buildUserCustomUserClient("fulano");
    }
    @Test
    void hasRoleAdminOrIsAuthorizedUserShouldDoNothingWhenOtherUser(){
        when(userService.authenticated()).thenReturn(selfUser);
        Long useId = otherUser.getId();
        Assertions.assertThrows(ForbiddenException.class,()->{
            authService.hasRoleAdminOrIsAuthorizedUser(useId);
        });
    }

    @Test
    void hasRoleAdminOrIsAuthorizedUserShouldDoNothingWhenSelfUser(){
        when(userService.authenticated()).thenReturn(selfUser);
        Long useId = selfUser.getId();
        Assertions.assertDoesNotThrow(()->{
            authService.hasRoleAdminOrIsAuthorizedUser(useId);
        });
    }
    @Test
    void hasRoleAdminOrIsAuthorizedUserShouldThrowForbiddenExceptionWhenAdminUser(){
        when(userService.authenticated()).thenReturn(adminUser);
        Long useId = adminUser.getId();
        Assertions.assertDoesNotThrow(()->{
            authService.hasRoleAdminOrIsAuthorizedUser(useId);
        });
    }

}
