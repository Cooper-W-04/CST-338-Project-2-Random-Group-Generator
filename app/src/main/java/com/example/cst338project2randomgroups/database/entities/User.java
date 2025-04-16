//TODO: make preferences on a table, have -1 be nagative, 1 be positive, and maybe have 0 as neutral
package com.example.cst338project2randomgroups.database.entities;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    private String firstName;
    private String lastName;
    private String password;
    private HashMap<String, ArrayList<Integer>> preferences;

    private boolean isStudent = false;
    private boolean isTeacher = false;
    private boolean isAdmin = false;
    public User(String firstName, String lastName, String password, String status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        setStatus(status);
    }

    private void setStatus(String status){
        if(status.equalsIgnoreCase("student")){
            isStudent = true;
        } else if(status.equalsIgnoreCase("teacher")){
            isTeacher = true;
        } else {
            isAdmin = true;
        }
    }

    public String getStatus(){
        if(isStudent){
            return "Student";
        } else if(isTeacher){
            return "Teacher";
        } else{
            return "Admin";
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
