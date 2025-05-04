package com.example.cst338project2randomgroups;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import android.app.Application;

import androidx.test.core.app.ApplicationProvider;

import com.example.cst338project2randomgroups.database.AppRepository;
import com.example.cst338project2randomgroups.database.entities.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DatabaseTest {
    AppRepository repository;

    @Before
    public void setup(){
        Application application = ApplicationProvider.getApplicationContext();
        repository = AppRepository.getRepository(application);
    }

    @After
    public void teardown(){
        repository = null;
    }

    @Test
    public void userTest(){
        User teacher1local = new User("teacher1", "teacher1", "teacher");
        User teacher1db = repository.getUserByUsername("teacher1").getValue();
        assertEquals(teacher1local, teacher1db);

        User teacher2 = repository.getUserByUsername("teacher2").getValue();
        assertNotEquals(teacher1local, teacher2);
    }
}
