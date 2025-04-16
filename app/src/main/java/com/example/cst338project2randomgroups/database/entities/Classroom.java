//TODO: make more interactions between classroom and user
package com.example.cst338project2randomgroups.database.entities;

import java.util.ArrayList;

public class Classroom {
    private User teacher;
    private String className;
    private ArrayList<User> students;

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
    }
}
