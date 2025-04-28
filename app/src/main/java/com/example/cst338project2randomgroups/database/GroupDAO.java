package com.example.cst338project2randomgroups.database;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.cst338project2randomgroups.database.entities.Group;

@Dao
public interface GroupDAO {
    @Insert
    void insert(Group group);
}
