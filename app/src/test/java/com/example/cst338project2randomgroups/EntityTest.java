package com.example.cst338project2randomgroups;


import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.cst338project2randomgroups.database.entities.Classroom;
import com.example.cst338project2randomgroups.database.entities.User;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EntityTest {
    User user1;
    User user2;
    User user3;

    Classroom classroom1;
    Classroom classroom2;
    Classroom classroom3;

    @BeforeEach
    public void setup(){
        //user setup
        user1 = new User("cooper", "cooper", "student");
        user2 = new User("ann", "ann", "teacher");
        user3 = user1;
        user3.setRole("teacher");

        //classroom setup
        classroom1 = new Classroom(user2.getUserId(), "cst338");
        classroom2 = new Classroom(user3.getUserId(), "cst338");
        classroom3 = classroom1;
        classroom1.setTeacherId(user3.getUserId());
    }

    @AfterEach
    public void teardown(){
        user1 = null;
        user2 = null;
        user3 = null;
        classroom1 = null;
        classroom2 = null;
        classroom3 = null;
    }

    @Test
    public void studentTest(){
        assertNotEquals(user1, user2);
    }
}
