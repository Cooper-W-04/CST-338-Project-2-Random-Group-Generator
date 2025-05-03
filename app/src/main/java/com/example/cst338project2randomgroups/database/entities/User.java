package com.example.cst338project2randomgroups.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.cst338project2randomgroups.database.ClassroomDAO;
import com.example.cst338project2randomgroups.database.RosterDAO;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "users")
public class User {
    @PrimaryKey(autoGenerate = true)
    private int userId;
    private String username;
    private String password;
    private String role;
    private boolean isAdmin = false;
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.isAdmin = role.equalsIgnoreCase("Admin");
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
        setAdmin(role.equalsIgnoreCase("admin"));
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
