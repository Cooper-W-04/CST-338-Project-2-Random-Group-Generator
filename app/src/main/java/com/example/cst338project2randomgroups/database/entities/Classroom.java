package com.example.cst338project2randomgroups.database.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.cst338project2randomgroups.database.GroupDAO;
import com.example.cst338project2randomgroups.database.RosterDAO;
import com.example.cst338project2randomgroups.database.UserDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Entity(tableName = "classrooms",
        foreignKeys = {
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "userId",
                        childColumns = "teacherId",
                        onDelete = ForeignKey.CASCADE
                )})
public class Classroom {
    @PrimaryKey(autoGenerate = true)
    private int classroomId;
    private int teacherId;
    private String className;
    private boolean groupsCreated = false;
    private int groupSize = 0;

    public Classroom(int teacherId, String className){
        this.teacherId = teacherId;
        this.className = className;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(int classroomId) {
        this.classroomId = classroomId;
    }

    public User getTeacher(UserDAO userDAO) {
        return userDAO.getUserById(teacherId).getValue();
    }

    public boolean isGroupsCreated() {
        return groupsCreated;
    }

    public void setGroupsCreated(boolean groupsCreated) {
        this.groupsCreated = groupsCreated;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Classroom classroom = (Classroom) o;
        return teacherId == classroom.teacherId && groupsCreated == classroom.groupsCreated && Objects.equals(className, classroom.className);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teacherId, className, groupsCreated);
    }
}
