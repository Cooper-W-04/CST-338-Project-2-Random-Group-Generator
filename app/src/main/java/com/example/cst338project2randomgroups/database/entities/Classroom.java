//TODO: make more interactions between classroom and user for features
//TODO: make all the features that need to be made, this is how it is because of timing
package com.example.cst338project2randomgroups.database.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.cst338project2randomgroups.database.GroupDAO;
import com.example.cst338project2randomgroups.database.RosterDAO;
import com.example.cst338project2randomgroups.database.UserDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Entity(tableName = "classrooms",
        foreignKeys = {
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "userId",
                        childColumns = "teacherId",
                        onDelete = ForeignKey.CASCADE
                )})
public class Classroom {
    @PrimaryKey(autoGenerate = true)
    private int classroomId;
    private int teacherId;
    private String className;
    private boolean groupsCreated = false;

    public Classroom(int teacherId, String className){
        this.teacherId = teacherId;
        this.className = className;
    }

    public void createGroups(int size, RosterDAO rosterDao, UserDAO userDao, GroupDAO groupDao){
        List<Roster> roster = rosterDao.getAllRostersByClassroomId(classroomId);
        int groupNum = ((roster.size())/size) + 1;
        int peopleInGroups = 0;
        for(int i = 0; i<groupNum; i++){
            for(int k = 0; k < size; k++){
                if(peopleInGroups == roster.size()){
                    break;
                }
                User randomKid = getRandomStudentFromClass(rosterDao, userDao);

                //TODO: use logic to see if the user is in a group in the classroom, and if it is, get a different random student

                Group group = new Group(classroomId, randomKid.getUserId(), size);
                groupDao.insert(group);
                peopleInGroups++;
            }
        }
        groupsCreated = true;
    }

    public User getRandomStudentFromClass(RosterDAO rosterDao, UserDAO userDao){
        List<Roster> roster = rosterDao.getAllRostersByClassroomId(classroomId);
        Random random = new Random();
        int rosterNum = random.nextInt(roster.size());
        return userDao.getUserById(roster.get(rosterNum).getStudentId()).getValue();
    }

    public void fillGroup(){
        if()
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(int classroomId) {
        this.classroomId = classroomId;
    }

    public User getTeacher(UserDAO userDAO) {
        return userDAO.getUserById(teacherId).getValue();
    }

    public boolean isGroupsCreated() {
        return groupsCreated;
    }

    public void setGroupsCreated(boolean groupsCreated) {
        this.groupsCreated = groupsCreated;
    }

    public List<User> getStudents(UserDAO userDAO, RosterDAO rosterDAO) {
        List<User> students = new ArrayList<>();
        for (Roster roster : rosterDAO.getAllRosters()) {
            if (roster.getClassroomId() == classroomId) {
                User student = userDAO.getUserById(roster.getStudentId()).getValue();
                if (student != null && student.getRole().equalsIgnoreCase("student")) {
                    students.add(student);
                }
            }
        }
        return students;
    }
}
