//TODO: make more interactions between classroom and user
//TODO: make all the features that need to be made, this is how it is because of timing
package com.example.cst338project2randomgroups.database.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.cst338project2randomgroups.database.ClassroomDAO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

@Entity(tableName = "classrooms",
        foreignKeys = @ForeignKey(
                entity = User.class,
                parentColumns = "userId",
                childColumns = "teacherId",
                onDelete = ForeignKey.CASCADE),
        indices = {@Index("teacherId"), @Index(value = {"className"}, unique = true)})
public class Classroom {
    @PrimaryKey(autoGenerate = true)
    private int classroomId;
    private int teacherId;
    private String className;
//    private ArrayList<User> students;
//    private ArrayList<User[]> groups;

    public Classroom(int teacherId, String className){
        this.teacherId = teacherId;
        this.className = className;
//        students = new ArrayList<>();
    }

    public void addStudent(User student){
        if(!student.getRole().equalsIgnoreCase("student")){
            //case that the user is not a student
            return;
        }
//        students.add(student);
//        for(int i = 0; i<students.size(); i++){
//            students.get(i).updatePreference(0, student.getUsername(), className);
//        }
    }

    public void createGroups(int size){
        //TODO: make code to see how many groups need to be made, also the rest of the method
    }

//    TODO: make this return the correct thing
//    public User getTeacher() {
//        return getUserById(teacherId);
//    }



    public static Classroom getClassroomByName(String name){
        //TODO: make this actually work and move it to where it's supposed to be
        //TODO: maybe make this be in the database
        return new Classroom(1, "test");
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
