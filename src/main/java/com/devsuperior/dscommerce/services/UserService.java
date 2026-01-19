package com.devsuperior.dscommerce.services;
import com.devsuperior.dscommerce.dto.UserDTO;
import com.devsuperior.dscommerce.entities.Role;
import com.devsuperior.dscommerce.entities.User;
import com.devsuperior.dscommerce.projections.UserDetailsProjection;
import com.devsuperior.dscommerce.repositories.UserRepository;
import com.devsuperior.dscommerce.services.util.CustomUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomUserUtil customUserUtil;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDetailsProjection> response = userRepository.getUserByEmailWithRole(username);
        if(response.isEmpty()){
            throw new UsernameNotFoundException("User not found");
        }
        User user = new User();
        user.setEmail(username);
        user.setPassword(response.getFirst().getPassword());
        response.forEach(u -> {
            Role role = new Role(u.getRoleId(),u.getAuthority());
            user.addRole(role);
        });
        user.getAuthorities().forEach(r ->{  System.out.println(r.getAuthority());});
        return user;
    }

    protected User authenticated(){
        try {
            String username = customUserUtil.getLoggedUser();
            return userRepository.findUserByEmail(username).get();
        }catch (Exception e){
           throw new UsernameNotFoundException("User not found") ;
        }
    }

    @Transactional(readOnly = true)
    public UserDTO getMe(){
        return UserDTO.fromUser(authenticated());
    }
}
