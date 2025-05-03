//made by Cooper Westervelt :3

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
        user3 = new User("cooper", "cooper", "teacher");

        //classroom setup
        classroom1 = new Classroom(user2.getUserId(), "cst338");
        classroom2 = new Classroom(user3.getUserId(), "cst231");
        classroom3 = new Classroom(user2.getUserId(), "something else");
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
    public void userTest(){
        //basic tests
        assertNotEquals(user1, user2);
        assertEquals(user1.getUsername(), user3.getUsername());
        assertEquals(user1.getPassword(), user3.getPassword());
        assertNotEquals(user1, user3);

        //getter and setter tests
        user1.setUsername("sarah");
        assertEquals("sarah", user1.getUsername());
        assertNotEquals(user1.getUsername(), "cooper");

        String user3pass = user3.getPassword();
        user3.setPassword(user2.getPassword());
        assertEquals("ann", user3.getPassword());
        assertNotEquals(user3pass, user3.getPassword());
    }

    @Test
    public void classroomTest(){
        assertNotEquals(classroom1, classroom2);

        //getter and setter dumb stuff
        classroom2.setTeacherId(user3.getUserId());
        assertEquals(user3.getUserId(), classroom2.getTeacherId());
    }
}
