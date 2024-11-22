package com.javacorner.admin.dao;

import com.javacorner.admin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long>{
    // we don't need to write the query because email is a User field
    User findByEmail(String email);

}
