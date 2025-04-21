package com.example.cst338project2randomgroups.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cst338project2randomgroups.database.entities.User;

@Dao
public interface UserDAO {
    @Insert
    long insertUser(User user);

    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    User getUserById(int id);

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    User getUserByUsername(String username);

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);
}
