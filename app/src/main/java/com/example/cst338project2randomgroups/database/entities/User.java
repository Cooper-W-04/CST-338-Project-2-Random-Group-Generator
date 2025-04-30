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

    public boolean joinClassroom(String classroomName, RosterDAO rosterDAO, ClassroomDAO classroomDao) {
        if (!role.equals("student")) {
            return false;
        }

        Classroom classroom = classroomDao.getClassroomByName(classroomName);
        if (classroom == null) {
            return false;
        }

        int classroomId = classroom.getClassroomId();
        List<Roster> rostersInClass = rosterDAO.getAllRostersByClassroomId(classroomId);

        for (Roster roster : rostersInClass) {
            if (roster.getStudentId() == userId) {
                return false;
            }
        }

        Roster newRoster = new Roster(userId, classroomId);
        rosterDAO.insert(newRoster);
        return true;
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
        this.isAdmin = role.equalsIgnoreCase("Admin");
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
