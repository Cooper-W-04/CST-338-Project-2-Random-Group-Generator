package com.example.cst338project2randomgroups.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cst338project2randomgroups.database.entities.Classroom;

import java.util.List;

@Dao
public interface ClassroomDAO {
    @Insert
    long insert(Classroom classroom);

    @Query("SELECT * FROM classrooms WHERE classroomId == :classroomId LIMIT 1")
    LiveData<Classroom> getClassroomById(int classroomId);

    @Query("SELECT * FROM classrooms WHERE className == :className LIMIT 1")
    LiveData<Classroom> getClassroomByName(String className);

    @Query("SELECT * FROM classrooms WHERE teacherId = :teacherId")
    LiveData<List<Classroom>> getClassroomsByTeacherId(int teacherId);

    @Query("SELECT * FROM classrooms")
    LiveData<List<Classroom>> getAllClassrooms();

    @Update
    void updateClassroom(Classroom classroom);

    @Delete
    void deleteClassroom(Classroom classroom);

    @Query("SELECT * FROM classrooms WHERE classroomId IN (:ids)")
    LiveData<List<Classroom>> getClassroomsByIds(List<Integer> ids);
}
