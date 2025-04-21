//TODO: make preferences on a table, have -1 be negative, 1 be positive, and maybe have 0 as neutral
//TODO: make all the features that need to be made, this is how it is because of timing
package com.example.cst338project2randomgroups.database.entities;

import static android.text.TextUtils.indexOf;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.HashMap;

@Entity(tableName = "users")
public class User {
    @PrimaryKey(autoGenerate = true)
    private int userId;
    private String username;
    private String password;
    private String role;
    private boolean isAdmin = false;
//    private HashMap<Integer, ArrayList<Integer>> preferences;
//    private ArrayList<Classroom> classes;
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

    public String getRole(){
        return role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String firstName) {
        this.username = firstName;
    }

    public void updatePreference(int preference, String name, String classId){
//TODO: fix this logic so that it works with tables
//        if(role.equalsIgnoreCase("Student")){
//            Classroom classroom = Classroom.getClassroomById(classId);
//            if(classes.contains(classroom)){
//                User kid = User.getUserByUsername(name);
//                ArrayList<User> kids = classroom.getStudents();
//                if(kids.contains(kid)){
//                    preferences.get(classroom).set(kids.indexOf(kid), preference);
//                } else{
//                    //student not in classroom
//                    return;
//                }
//            } else{
//                //student not in the class
//                return;
//            }
//        } else{
//            //user is not a student
//            return;
//        }
    }

    public static User getUserByUsername(String name){
        //TODO: make this actually work and move it to where it's supposed to be
        //TODO: also put it in the database class
        return new User("gamer", "gamer", "student");
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
