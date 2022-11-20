package com.bittnettraning.admin.services.impl;

import com.bittnettraning.admin.repositories.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    @Test
    void testFindRoleByName() {
        roleService.findRoleByName("Admin");
        verify(roleRepository).findByName(any());
    }

    @Test
    void createRole() {
        roleService.createRole("Admin");
        verify(roleRepository, times(1)).save(any());
    }
}