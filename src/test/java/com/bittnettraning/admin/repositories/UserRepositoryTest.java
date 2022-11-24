package com.bittnettraning.admin.repositories;

import com.bittnettraning.admin.AbstractTest;
import com.bittnettraning.admin.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@Sql(scripts = {"file:src/test/resources/db/clearAll.sql","file:src/test/resources/db/admin-db.sql"})
class UserRepositoryTest extends AbstractTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByEmail() {
        User user = userRepository.findByEmail("bogdanDumi@yahoo.com");
        Long expectedId = 4L;
        assertEquals(expectedId, user.getUserId());
    }

    @Test
    void testFindingNonExistingUserWithName(){
        User user = userRepository.findByEmail("noneExistingEmail@yahoo.com");
        assertNull(user);
    }
}