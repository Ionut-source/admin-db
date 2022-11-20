package com.bittnettraning.admin.services;

import com.bittnettraning.admin.entities.Role;

public interface RoleService {

    Role findRoleByName(String roleName);

    Role createRole(String roleName);
}
