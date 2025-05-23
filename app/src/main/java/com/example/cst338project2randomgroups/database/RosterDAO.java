package com.example.cst338project2randomgroups.database;

import androidx.lifecycle.LiveData;
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
    LiveData<Roster> getRosterById(int rosterId);

    @Query("SELECT * FROM rosters WHERE classroomId = :classroomId")
    LiveData<List<Roster>> getAllRostersByClassroomId(int classroomId);

    @Query("SELECT * FROM rosters")
    LiveData<List<Roster>> getAllRosters();

//    @Query("SELECT * FROM rosters WHERE classroomId = :classroomId")
//    List<Roster> getAllRostersByClassroomIdNow(int classroomId);

    @Query("SELECT * FROM rosters WHERE studentId = :studentId")
    LiveData<List<Roster>> getRostersByStudentId(int studentId);

    @Update
    void updateRoster(Roster roster);

    @Delete
    void deleteRoster(Roster roster);
}