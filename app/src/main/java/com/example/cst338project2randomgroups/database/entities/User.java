//TODO: make preferences on a table, have -1 be nagative, 1 be positive, and maybe have 0 as neutral
package com.example.cst338project2randomgroups.database.entities;

import static android.text.TextUtils.indexOf;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    private String username;
    private String password;
    private HashMap<Classroom, ArrayList<Integer>> preferences;
    private ArrayList<Classroom> classes;

    private boolean isStudent = false;
    private boolean isTeacher = false;
    private boolean isAdmin = false;
    public User(String username, String password, String status) {
        this.username = username;
        this.password = password;
        setType(status);
        classes = new ArrayList<>();
    }

    private void setType(String status){
        if(status.equalsIgnoreCase("student")){
            preferences = new HashMap<>();
            isStudent = true;
        } else if(status.equalsIgnoreCase("teacher")){
            preferences = null;
            isTeacher = true;
        } else {
            preferences = null;
            isAdmin = true;
        }
    }

    public String getType(){
        if(isStudent){
            return "Student";
        } else if(isTeacher){
            return "Teacher";
        } else{
            return "Admin";
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String firstName) {
        this.username = firstName;
    }

    public void updatePreference(int preference, String name, String className){
        if(isStudent){
            Classroom classroom = Classroom.getClassroomByName(className);
            if(classes.contains(classroom)){
                User kid = User.getUserByUsername(name);
                ArrayList<User> kids = classroom.getStudents();
                if(kids.contains(kid)){
                    preferences.get(classroom).set(kids.indexOf(kid), preference);
                } else{
                    //student not in classroom
                    return;
                }
            } else{
                //student not in the class
                return;
            }
        } else{
            //user is not a student
            return;
        }
    }

    public static User getUserByUsername(String name){
        //TODO: make this actually work and move it to where it's supposed to be
        return new User("gamer", "gamer", "student");
    }
}
