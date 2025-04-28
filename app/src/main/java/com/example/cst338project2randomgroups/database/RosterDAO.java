package com.example.cst338project2randomgroups.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cst338project2randomgroups.database.entities.Roster;

import java.util.List;

@Dao
public interface RosterDAO {

    @Insert
    long insert(Roster roster);

    @Query("SELECT * FROM rosters WHERE rosterId = :rosterId LIMIT 1")
    Roster getRosterById(int rosterId);

    @Query("SELECT * FROM rosters WHERE classroomId = :classroomId LIMIT 1")
    Roster getRosterByClassroomId(int classroomId);

    @Query("SELECT * FROM rosters")
    List<Roster> getAllRosters();

    @Query("SELECT * FROM rosters WHERE studentId = :studentId")
    List<Roster> getRostersByStudentId(int studentId);

    @Update
    void updateRoster(Roster roster);

    @Delete
    void deleteRoster(Roster roster);
}