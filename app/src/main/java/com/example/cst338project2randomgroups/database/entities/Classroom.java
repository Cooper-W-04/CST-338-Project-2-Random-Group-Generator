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

@Entity(tableName = "classrooms")
public class Classroom {
    @PrimaryKey(autoGenerate = true)
    private int classroomId;
    private int teacherId;
    private String className;
    private int rosterId;

    public Classroom(int teacherId, String className){
        this.teacherId = teacherId;
        this.className = className;
        Roster roster = new Roster(classroomId);
//        TODO: make this work
//        roster.insert();
        this.rosterId = roster.getRosterId();
    }

//TODO: make this work
//    public void addStudent(User student){
//        if(!student.getRole().equalsIgnoreCase("student")){
//            return;
//        }
//        this.getRosterById(rosterId).addStudent(student.getUserId());
//    }

    public void createGroups(int size){
        //TODO: make code to see how many groups need to be made, also the rest of the method
    }

    public User getTeacher(UserDAO userDAO){
        return userDAO.getUserById(teacherId).getValue();
    }


//TODO: make this able to be here
//    public List<User> getStudents(UserDAO userDAO) {
//        List<User> students = new ArrayList<>();
//        for (int studentId : getRosterById(rosterId).getStudentIds()) {
//            students.add(userDAO.getUserById(studentId).getValue());
//        }
//        return students;
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

    public int getRosterId() {
        return rosterId;
    }

    public void setRosterId(int rosterId) {
        this.rosterId = rosterId;
    }
}
