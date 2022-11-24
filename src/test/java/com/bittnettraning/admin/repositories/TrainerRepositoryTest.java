package com.bittnettraning.admin.repositories;

import com.bittnettraning.admin.AbstractTest;
import com.bittnettraning.admin.entities.Trainer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@Sql(scripts = {"file:src/test/resources/db/clearAll.sql", "file:src/test/resources/db/admin-db.sql"})
class TrainerRepositoryTest extends AbstractTest {

    @Autowired
    private TrainerRepository trainerRepository;

    @Test
    void testFindTrainerByName() {
        List<Trainer> trainers = trainerRepository.findTrainerByName("Adrian");
        int expectedValue = 1;
        assertEquals(expectedValue, trainers.size());
    }

    @Test
    void testFindTrainerByEmail() {
        Trainer expectedTrainer = new Trainer();
        expectedTrainer.setTrainerId(1L);
        expectedTrainer.setFirstName("Adrian");
        expectedTrainer.setLastName("Anghel");
        expectedTrainer.setSummary("Experienced trainer");

        Trainer trainer = trainerRepository.findTrainerByEmail("adrianA@gmail.com");

        assertEquals(expectedTrainer, trainer);

    }

        @Test
        void testFindTrainerByNotExistingEmail() {
            Trainer trainer = trainerRepository.findTrainerByEmail("admin@gmail.com");
            assertNull(trainer);
        }
    }