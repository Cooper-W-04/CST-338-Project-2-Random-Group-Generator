package com.example.cst338project2randomgroups.database.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "groups",
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
public class Group {
    @PrimaryKey(autoGenerate = true)
    private int groupId;
    private int groupNumber;
    private int classroomId;
    private int studentId;
    private int groupSize;

    public Group(int classroomId, int studentId, int groupSize, int groupNumber){
        this.classroomId = classroomId;
        this.studentId = studentId;
        this.groupSize = groupSize;
        this.groupNumber = groupNumber;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
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

    public int getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    }

    public int getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(int groupNumber) {
        this.groupNumber = groupNumber;
    }
}
