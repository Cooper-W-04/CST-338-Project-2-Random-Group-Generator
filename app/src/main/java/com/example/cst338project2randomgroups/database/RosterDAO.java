package com.example.cst338project2randomgroups.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cst338project2randomgroups.database.entities.Classroom;
import com.example.cst338project2randomgroups.database.entities.Roster;
import com.example.cst338project2randomgroups.database.entities.User;

@Dao
public interface RosterDAO {
    @Insert
    long insert(Roster roster);

    @Query("SELECT * FROM classrooms WHERE rosterId == :rosterId LIMIT 1")
    Classroom getRosterById(int rosterId);

    @Query("SELECT * FROM classrooms WHERE classroomId == :classroomId LIMIT 1")
    Classroom getRosterByClassId(int classroomId);

    @Update
    void updateRoster(Roster roster);

    @Delete
    void deleteRoster(Roster roster);
}
