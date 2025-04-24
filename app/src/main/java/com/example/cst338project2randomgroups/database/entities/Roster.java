package com.example.cst338project2randomgroups.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.cst338project2randomgroups.database.typeConverters.ListConverters;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "rosters")
@TypeConverters(ListConverters.class)
public class Roster {
    @PrimaryKey (autoGenerate = true)
    private int rosterId;
    private int classroomId;
    private int studentId;

    @TypeConverters(ListConverters.class) // COMMENT: Tells Room to use the custom converter for List<Integer>
    private List<Integer> studentIds;
    public Roster(int classroomId){
        this.classroomId = classroomId;
        this.studentIds = new ArrayList<>();
    }

    public void addStudent(int studentId) {
        if (!studentIds.contains(studentId)) {
            studentIds.add(studentId);
        }
    }

    public void removeStudent(int studentId) {
        studentIds.remove(Integer.valueOf(studentId));
    }

    public boolean containsStudent(int studentId) {
        return studentIds.contains(studentId);
    }

    public List<Integer> getStudentIds() {
        return studentIds;
    }

    public int getRosterId() {
        return rosterId;
    }

    public void setRosterId(int rosterId) {
        this.rosterId = rosterId;
    }

    public int getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(int classroomId) {
        this.classroomId = classroomId;
    }
}
