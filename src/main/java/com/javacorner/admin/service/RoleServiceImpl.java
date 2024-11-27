package com.javacorner.admin.service;

import com.javacorner.admin.dao.RoleDao;
import com.javacorner.admin.entity.Role;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private RoleDao roleDao;

    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public Role createRole(String roleName) {
        return roleDao.save(new Role(roleName));
    }
}
