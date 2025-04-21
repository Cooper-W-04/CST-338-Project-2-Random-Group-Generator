package com.example.cst338project2randomgroups.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cst338project2randomgroups.database.entities.Classroom;

@Dao
public interface ClassroomDAO {
    @Insert
    long insertClassroom(Classroom classroom);

    @Query("SELECT * FROM classrooms WHERE classroomId == :classroomId LIMIT 1")
    Classroom getClassroomById(int classroomId);

    @Query("SELECT * FROM classrooms WHERE className == :className LIMIT 1")
    Classroom getClassroomByName(String className);

    @Update
    void updateClassroom(Classroom classroom);

    @Delete
    void deleteClassroom(Classroom classroom);
}
