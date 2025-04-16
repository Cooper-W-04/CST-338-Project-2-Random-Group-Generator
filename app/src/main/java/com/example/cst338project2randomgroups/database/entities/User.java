//TODO: make preferences on a table, have -1 be nagative, 1 be positive, and maybe have 0 as neutral
package com.example.cst338project2randomgroups.database.entities;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    private String username;
    private String password;
    private HashMap<String, ArrayList<Integer>> preferences;

    private boolean isStudent = false;
    private boolean isTeacher = false;
    private boolean isAdmin = false;
    public User(String username, String password, String status) {
        this.username = username;
        this.password = password;
        setType(status);
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

    public void setUserame(String firstName) {
        this.username = firstName;
    }
}
