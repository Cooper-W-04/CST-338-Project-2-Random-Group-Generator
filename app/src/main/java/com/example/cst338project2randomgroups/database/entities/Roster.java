package com.example.cst338project2randomgroups.database.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "rosters",
        primaryKeys = {"classroomId", "studentId"},
        foreignKeys = {
                @ForeignKey(
                        entity = Classroom.class,
                        parentColumns = "classroomId",
                        childColumns = "classroomId",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "userId",
                        childColumns = "studentId",
                        onDelete = ForeignKey.CASCADE
                )})
public class Roster {
    private int rosterId;
    private int classroomId;
    private int studentId;
    public Roster(int classroomId, int studentId){
        this.classroomId = classroomId;
        this.studentId = studentId;
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

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
}
