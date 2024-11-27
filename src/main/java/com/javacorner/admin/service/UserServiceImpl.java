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
public class UserServiceImpl implements  UserService{


    private UserDao userDao ;
    private RoleDao roleDao ;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    @Override
    public User loadUserByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public User createUser(String email, String password) {
        return userDao.save(new User(email,password));
    }

    @Override
    public void asssignRoleToUser(String email, String roleName) {
        User user = userDao.findByEmail(email) ;
        Role role = roleDao.findByName(roleName);
        user.assignRoleToUser(role);
        //userDao.save(user);

    }
}
