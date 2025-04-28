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

    @Query("SELECT * FROM groups WHERE classroomId = :classroomId")
    List<Group> getAllGroupsByClassroomId(int classroomId);

    @Query("DELETE FROM groups WHERE classroomId = :classroomId")
    void deleteAllGroupsByClassroomId(int classroomId);

    @Query("SELECT * FROM groups WHERE studentId = :studentId AND classroomId = :classroomId LIMIT 1")
    Group getGroupByStudentIdAndClassroomId(int studentId, int classroomId);
}