//TODO: make preferences on a table, have -1 be nagative, 1 be positive, and maybe have 0 as neutral
package com.example.cst338project2randomgroups.database.entities;

import java.util.ArrayList;

public class User {
    private String firstName;
    private String lastName;
    private String password;
    private ArrayList<User> positivePreferences;
    private ArrayList<User> negativePreferences;

    private boolean isStudent = false;
    private boolean isTeacher = false;
    private boolean isAdmin = false;
    public User(String firstName, String lastName, String password, String status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        if(status.equalsIgnoreCase("student")){
            this.isStudent = true;
            positivePreferences = new ArrayList<User>();
            negativePreferences = new ArrayList<User>();
        } else if(status.equalsIgnoreCase("teacher")){
            this.isTeacher = true;
        } else {
            this.isAdmin = true;
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
