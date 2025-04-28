package com.example.cst338project2randomgroups.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.cst338project2randomgroups.database.entities.Group;

import java.util.List;

@Dao
public interface GroupDAO {
    @Insert
    void insert(Group group);

    @Query("SELECT * FROM rosters WHERE classroomId = :classroomId")
    List<Group> getAllGroupsByClassroomId(int classroomId);
}
