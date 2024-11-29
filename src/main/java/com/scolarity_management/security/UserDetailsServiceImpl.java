package com.scolarity_management.security;

import com.scolarity_management.entity.User;
import com.scolarity_management.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
//6
    // This is a service class that will be used to load the user from the database and create a UserDetails object
    // a UserDetails object is used by spring security to authenticate and authorize the user
    private UserService userService;

    public UserDetailsServiceImpl(UserService userService) {

        this.userService = userService;
    }

    /**
     *Upon logging in , the spring security will require us to create a user with the type of userDetails using the logged in user
     * so that we fetch the user from the db , aquire the authorities , and create an object of the type of userDetails.user
     * and we will do a return after which the spring security continues the authernication process
     */

    // This method will be called by the spring security to load the user from the database using the email
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.loadUserByEmail(email);
        if (user==null) {
            throw new UsernameNotFoundException("User not " +
                    "found " +
                    "with email: " + email);
        }
        // Create a list of authorities from the user roles
        // GrantedAuthority is an interface that represents an authority granted to an Authentication object it is used to represent the roles of the user
        Collection<GrantedAuthority> authorities= new ArrayList<>();
        // Here we are creating a SimpleGrantedAuthority object for each role of the user and adding it to the authorities list
        // SimpleGrantedAuthority is a concrete implementation of the GrantedAuthority interface , it is used to represent the roles of the user
        user.getRoles().forEach(role -> {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
            authorities.add(authority);
        });
        // Create a UserDetails object using the user email , password and authorities
        org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
        return userDetails;
    }
}
