package com.bittnettraning.admin.services;

import com.bittnettraning.admin.entities.User;

public interface UserService {

    User findUserByEmail(String email);

    User createUser(String email, String password);

    void assignRoleToUser(String email, String roleName);

    boolean doesCurrentUserHasRole(String roleName);
}
