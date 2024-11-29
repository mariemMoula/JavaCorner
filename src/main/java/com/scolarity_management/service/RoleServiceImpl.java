package com.scolarity_management.service;

import com.scolarity_management.dao.RoleDao;
import com.scolarity_management.entity.Role;
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
