//TODO: make preferences on a table, have -1 be negative, 1 be positive, and maybe have 0 as neutral
//TODO: make all the features that need to be made, this is how it is because of timing
package com.example.cst338project2randomgroups.database.entities;

import static android.text.TextUtils.indexOf;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.cst338project2randomgroups.database.ClassroomDAO;
import com.example.cst338project2randomgroups.database.RosterDAO;

import java.util.ArrayList;
import java.util.HashMap;
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
        if(role.equalsIgnoreCase("Admin")){
            //allow the admin to edit all classes
            setAdmin(true);
        } else if(role.equalsIgnoreCase("teacher")){
            //make the list of classrooms the ones they're teaching, which when a user is created, should be nothing
        } else{
            //sets the preferences to not null and makes their classes the one's they're a part of, which should be null
        }
//        classes = new ArrayList<>();
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public List<Classroom> getClassrooms(ClassroomDAO classroomDAO, RosterDAO rosterDAO) {
        if (role.equalsIgnoreCase("admin")) {
            return classroomDAO.getAllClassrooms(); // assumes this method exists
        } else if (role.equalsIgnoreCase("teacher")) {
            return classroomDAO.getClassroomsByTeacherId(userId);
        } else if (role.equalsIgnoreCase("student")) {
            List<Classroom> result = new ArrayList<>();
            for (Roster roster : rosterDAO.getAllRosters()) {
                if (roster.getStudentIds().contains(userId)) {
                    Classroom classroom = classroomDAO.getClassroomById(roster.getClassroomId());
                    result.add(classroom);
                }
            }
            return result;
        } else {
            return new ArrayList<>();
        }
    }
}
