package com.bittnettraning.admin.services.impl;

import com.bittnettraning.admin.entities.Role;
import com.bittnettraning.admin.entities.User;
import com.bittnettraning.admin.repositories.RoleRepository;
import com.bittnettraning.admin.repositories.UserRepository;
import com.bittnettraning.admin.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;


    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User createUser(String email, String password) {
        return userRepository.save(new User(email, password));
    }

    @Override
    public void assignRoleToUser(String email, String roleName) {
        User user = findUserByEmail(email);
        Role role = roleRepository.findByName(roleName);
        user.assignRoleToUser(role);

    }
}
