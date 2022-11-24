package com.bittnettraning.admin.repositories;

import com.bittnettraning.admin.AbstractTest;
import com.bittnettraning.admin.entities.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@Sql(scripts = {"file:src/test/resources/db/clearAll.sql", "file:src/test/resources/db/admin-db.sql"})
class RoleRepositoryTest extends AbstractTest {


    @Autowired
    private RoleRepository roleRepository;

    @Test
    void testFindByName() {
        String expectedRoleName = "Admin";
        Role role = roleRepository.findByName(expectedRoleName);
        assertEquals(expectedRoleName, role.getName());
    }

    @Test
    void testFindNotExistingRole() {
        String roleName = "NewRole";
        Role role = roleRepository.findByName(roleName);
        assertNull(role);

    }
}