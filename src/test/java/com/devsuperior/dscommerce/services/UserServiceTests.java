package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.entities.User;
import com.devsuperior.dscommerce.projections.UserDetailsProjection;
import com.devsuperior.dscommerce.repositories.UserRepository;
import com.devsuperior.dscommerce.services.util.CustomUserUtil;
import com.devsuperior.dscommerce.util.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class UserServiceTests {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CustomUserUtil customUserUtil;
    private User user;
    private UserDetailsProjection userDetailsProjection;
    private List<UserDetailsProjection> userDetailsProjectionList;
    private String exitingUsername;
    private String unexistingUsername;

    @BeforeEach
    void setUp(){
        exitingUsername = "alexsandro@gmail.com";
        unexistingUsername ="otelo@gmail.com";
        userDetailsProjection = Factory.buildUserDetailsProjection(exitingUsername);
        userDetailsProjectionList = List.of(userDetailsProjection);
        user = Factory.buildUserClient();
        when(userRepository.getUserByEmailWithRole(exitingUsername)).thenReturn(userDetailsProjectionList);
        when(userRepository.getUserByEmailWithRole(unexistingUsername)).thenReturn(List.of());
        when(userRepository.findUserByEmail(any())).thenReturn(Optional.of(user));

    }
   @Test
    public void loadUserByUsernameShouldReturnUserDetailsWhenValidUsername(){
        var response = userService.loadUserByUsername(exitingUsername);
        String authority = response.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining());
        Assertions.assertNotNull(response);
        Assertions.assertEquals(exitingUsername,response.getUsername());
        Assertions.assertEquals(userDetailsProjection.getAuthority(),authority);
        Assertions.assertEquals(userDetailsProjection.getPassword(),response.getPassword());
    }

    @Test
    public void loadUserByUsernameShouldThrowUsernameNotFoundExceptionWhenUnexistingUsername(){
        Assertions.assertThrows(UsernameNotFoundException.class,()->{
            userService.loadUserByUsername(unexistingUsername);
        });
    }

    @Test
    public void authenticatedShouldReturnUserWhenLoggedUser(){
        when(customUserUtil.getLoggedUser()).thenReturn(user.getName());
        var response = userService.authenticated();
        Assertions.assertNotNull(response);
        Assertions.assertEquals(user.getId(),response.getId());
        Assertions.assertEquals(user.getName(),response.getName());
        Assertions.assertEquals(user.getUsername(),response.getUsername());
        Assertions.assertEquals(user.getPhone(),response.getPhone());
    }
@Test
   public void authenticatedShouldThrowUsernameNotFoundExceptionWhenNoLoggedUser(){
        doThrow(ClassCastException.class).when(customUserUtil).getLoggedUser();
        Assertions.assertThrows(UsernameNotFoundException.class, ()->{
            userService.authenticated();

        });
   }
}
