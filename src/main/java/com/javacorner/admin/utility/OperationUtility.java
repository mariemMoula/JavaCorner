package com.javacorner.admin.utility;

import com.javacorner.admin.dao.RoleDao;
import com.javacorner.admin.dao.UserDao;
import com.javacorner.admin.entity.Role;
import com.javacorner.admin.entity.User;
import jakarta.persistence.EntityNotFoundException;

public class OperationUtility {
// for launching
    public static void usersOperations(UserDao userDao){
        createUsers(userDao);
        updateUser(userDao);
        deleteUser(userDao);
        fetchUser(userDao);
    }
public static void rolesOperations(RoleDao roleDao){
        createRoles(roleDao);
        updateRole(roleDao);


}





    private static void createUsers(UserDao userDao) {
        User user1 = new User("pass1", "user1@gmail.com");
        userDao.save(user1);
        User user2 = new User("pass2", "user2@gmail.com");
        userDao.save(user2);
        User user3 = new User("pass3", "user3@gmail.com");
        userDao.save(user3);
        User user4 = new User("pass4", "user4@gmail.com");
        userDao.save(user4);

    }

    private static void updateUser(UserDao userDao) {
        User user = userDao.findById(1L).orElseThrow(()->new EntityNotFoundException("User not found"));
        user.setEmail("userEmailUpdated@gmail.com");
        userDao.save(user);

    }

    private static void deleteUser(UserDao userDao) {
        User user = userDao.findById(3L).orElseThrow(()->new EntityNotFoundException("User not found"));
        userDao.delete(user);
        // or simply use  userDao.deleteById(3L);
    }
    private static void fetchUser(UserDao userDao) {
        userDao.findAll().forEach(user -> System.out.println(user.toString()) );

    }

    private static void createRoles(RoleDao roleDao) {
        Role role1 = new Role("ADMIN");
        roleDao.save(role1);
        Role role2 = new Role("Instructor");
        roleDao.save(role2);
        Role role3 = new Role("Instructor");
        roleDao.save(role3);
    }

    private static void updateRole(RoleDao roleDao) {
    }


}
