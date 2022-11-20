package com.bittnettraning.admin.services.impl;

import com.bittnettraning.admin.entities.Role;
import com.bittnettraning.admin.repositories.RoleRepository;
import com.bittnettraning.admin.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findRoleByName(String roleName) {
        return roleRepository.findByName(roleName);
    }

    @Override
    public Role createRole(String roleName) {
        return roleRepository.save(new Role(roleName));
    }
}
