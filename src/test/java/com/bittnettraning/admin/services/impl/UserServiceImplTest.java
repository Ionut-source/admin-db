package com.bittnettraning.admin.services.impl;

import com.bittnettraning.admin.entities.Role;
import com.bittnettraning.admin.entities.User;
import com.bittnettraning.admin.repositories.RoleRepository;
import com.bittnettraning.admin.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private User mockedUser;
    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testFindUserByEmail() {
        userService.findUserByEmail("user1@gmail.com");
        verify(userRepository, times(1)).findByEmail(any());

    }

//    @Test
//    void testCreateUser() {
//        String email = "user1@gmail.com";
//        String password = "pass";
//        User user = new User(email, password);
//
//        userService.createUser(email, password);
//
//        ArgumentCaptor<User>  argumentCaptor = ArgumentCaptor.forClass(User.class);
//
//        verify(userRepository).save(argumentCaptor.capture());
//
//        User capturedUser = argumentCaptor.getValue();
//
//        assertEquals(user, capturedUser);
//
//    }

    @Test
    void testAssignRoleToUser() {
        Role role = new Role();
        role.setRoleId(1L);

        when(userRepository.findByEmail(any())).thenReturn(mockedUser);
        when(roleRepository.findByName(any())).thenReturn(role);

        userService.assignRoleToUser("email@gmail.com", "pass");

        verify(mockedUser, times(1)).assignRoleToUser(role);

    }
}