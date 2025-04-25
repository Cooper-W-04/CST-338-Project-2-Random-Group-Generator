//TODO: make preferences on a table, have -1 be negative, 1 be positive, and maybe have 0 as neutral
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

    public List<Classroom> getClassrooms(ClassroomDAO classroomDAO, RosterDAO rosterDAO) {
        if (role.equalsIgnoreCase("admin")) {
            return classroomDAO.getAllClassrooms();
        } else if (role.equalsIgnoreCase("teacher")) {
            return classroomDAO.getClassroomsByTeacherId(userId);
        } else if (role.equalsIgnoreCase("student")) {
            List<Classroom> result = new ArrayList<>();
            for (Roster roster : rosterDAO.getAllRosters()) {
                if (roster.getStudentId() == userId) {
                    Classroom classroom = classroomDAO.getClassroomById(roster.getClassroomId());
                    if (classroom != null) {
                        result.add(classroom);
                    }
                }
            }
            return result;
        } else {
            return new ArrayList<>();
        }
    }

    public boolean joinClassroom(int classroomId, RosterDAO rosterDAO) {
        //checks only student are joining the class
        if (!role.equals("student")) {
            return false;
        }

        //already enrolled in class
        for (Roster roster : rosterDAO.getAllRosters()) {
            if (roster.getStudentId() == userId && roster.getClassroomId() == classroomId) {
                return false;
            }
        }

        Roster newRoster = new Roster(userId, classroomId);
        rosterDAO.insert(newRoster);
        return true;
    }
}
