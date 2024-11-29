package com.scolarity_management.service;

import com.scolarity_management.dao.RoleDao;
import com.scolarity_management.dao.UserDao;
import com.scolarity_management.entity.Role;
import com.scolarity_management.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class UserServiceImpl implements UserService {


    private UserDao userDao;
    private RoleDao roleDao;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDao userDao, RoleDao roleDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User loadUserByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public User createUser(String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        return userDao.save(new User(encodedPassword,email ));
    }

    @Override
    public void assignRoleToUser(String email, String roleName) {
        System.out.println("Searching for user with email: " + email);
        User user = userDao.findByEmail(email);
        System.out.println("Found user: " + (user != null ? user.getEmail() : "null"));

        System.out.println("Searching for role with name: " + roleName);
        Role role = roleDao.findByName(roleName);
        System.out.println("Found role: " + (role != null ? role.getName() : "null"));

        if (user == null) {
            throw new IllegalArgumentException("User with email " + email + " not found");
        }
        if (role == null) {
            throw new IllegalArgumentException("Role with name " + roleName + " not found");
        }
        user.assignRoleToUser(role);
        userDao.save(user);
    }

}

