//TODO: make more interactions between classroom and user for features
//TODO: make all the features that need to be made, this is how it is because of timing
package com.example.cst338project2randomgroups.database.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.cst338project2randomgroups.database.ClassroomDAO;
import com.example.cst338project2randomgroups.database.UserDAO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Entity(
        tableName = "classrooms",
        foreignKeys = {
                // ✅ OPTIONAL: Enforce teacher link if you want referential integrity
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "userId",
                        childColumns = "teacherId",
                        onDelete = ForeignKey.CASCADE
                )
        }
)
public class Classroom {
    @PrimaryKey(autoGenerate = true)
    private int classroomId;
    private int teacherId;
    private String className;

    public Classroom(int teacherId, String className){
        this.teacherId = teacherId;
        this.className = className;
    }

    public void createGroups(int size){
        //TODO: make code to see how many groups need to be made, also the rest of the method
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
}
