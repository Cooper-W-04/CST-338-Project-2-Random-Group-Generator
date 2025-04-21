//TODO: make more interactions between classroom and user
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
                parentColumns = "id",
                childColumns = "teacherId",
                onDelete = ForeignKey.CASCADE),
        indices = {@Index("teacherId"), @Index(value = {"className"}, unique = true)})
public class Classroom {
    private ClassroomDAO classroomDao;
    @PrimaryKey(autoGenerate = true)
    private int classroomId;
    private int teacherId;
    private String className;
    private ArrayList<User> students;
    private ArrayList<User[]> groups;

    public Classroom(int teacherId, String className){
        this.teacherId = teacherId;
        this.className = className;
        students = new ArrayList<>();
    }

    public void addStudent(User student){
        if(!student.getRole().equalsIgnoreCase("student")){
            //case that the user is not a student
            return;
        }
        students.add(student);
        for(int i = 0; i<students.size(); i++){
            students.get(i).updatePreference(0, student.getUsername(), className);
        }
    }

    public void createGroups(int size){
        //TODO: make code to see how many groups need to be made, also the rest of the method
    }

//    TODO: make this return the correct thing
//    public User getTeacher() {
//        return getUserById(teacherId);
//    }

    public void setTeacher(User teacher) {
        this.teacherId = teacher.getUserId();
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public ArrayList<User> getStudents() {
        return students;
    }

    public static Classroom getClassroomByName(String name){
        //TODO: make this actually work and move it to where it's supposed to be
        //TODO: maybe make this be in the database
        return new Classroom(1, "test");
    }
}
