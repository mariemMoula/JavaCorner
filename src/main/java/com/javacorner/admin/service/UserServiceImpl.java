package com.javacorner.admin.service;

import com.javacorner.admin.dao.RoleDao;
import com.javacorner.admin.dao.UserDao;
import com.javacorner.admin.entity.Role;
import com.javacorner.admin.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class UserServiceImpl implements UserService {


    private UserDao userDao;
    private RoleDao roleDao;

    public UserServiceImpl(UserDao userDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    @Override
    public User loadUserByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public User createUser(String email, String password) {
        return userDao.save(new User(email, password));
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

