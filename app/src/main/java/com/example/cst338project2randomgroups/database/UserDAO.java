package com.example.cst338project2randomgroups.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cst338project2randomgroups.database.entities.User;

@Dao
public interface UserDAO {
    @Insert
    void insert(User user);

    @Query("SELECT * FROM users WHERE userId == :userId LIMIT 1")
    LiveData<User> getUserById(int userId);

    @Query("SELECT * FROM users WHERE username == :username LIMIT 1")
    LiveData<User> getUserByUsername(String username);

    @Query("DELETE from " + AppDatabase.USER_TABLE)
    void deleteAll();

    @Delete
    void deleteUser(User user);
}
