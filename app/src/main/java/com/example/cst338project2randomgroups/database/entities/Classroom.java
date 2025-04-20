//TODO: make more interactions between classroom and user
package com.example.cst338project2randomgroups.database.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Classroom {
    private User teacher;
    private String className;
    private ArrayList<User> students;
    private ArrayList<User[]> groups;

    public Classroom(User teacher, String className){
        this.teacher = teacher;
        this.className = className;
        students = new ArrayList<>();
    }

    public void addStudent(User student){
        if(!student.getType().equalsIgnoreCase("student")){
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

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
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
        return new Classroom(new User("gamer", "gamer", "teacher"), "test");
    }
}
