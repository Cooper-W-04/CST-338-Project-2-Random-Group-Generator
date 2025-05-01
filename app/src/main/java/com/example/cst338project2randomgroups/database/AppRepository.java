package com.example.cst338project2randomgroups.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.cst338project2randomgroups.database.entities.Classroom;
import com.example.cst338project2randomgroups.database.entities.Group;
import com.example.cst338project2randomgroups.database.entities.Roster;
import com.example.cst338project2randomgroups.database.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AppRepository {
    private final ClassroomDAO classroomDAO;
    private final UserDAO userDAO;
    private final RosterDAO rosterDAO;
    private final GroupDAO groupDAO;
    private static AppRepository repository;

    private AppRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        this.classroomDAO = db.classroomDAO();
        this.userDAO = db.userDAO();
        this.rosterDAO = db.rosterDAO();
        this.groupDAO = db.groupDAO();
    }

    public void insertUser(User user) {
        AppDatabase.databaseWriteExecutor.execute(() ->
                {
                    userDAO.insert(user);
                }
        );
    }

    public LiveData<User> getUserById(int userId) {
        return userDAO.getUserById(userId);
    }

    public LiveData<User> getUserByUsername(String username) {
        return userDAO.getUserByUsername(username);
    }

    public static AppRepository getRepository(Application application){
        if(repository != null){
            return repository;
        }
        Future<AppRepository> future = AppDatabase.databaseWriteExecutor.submit(
                new Callable<AppRepository>() {
                    @Override
                    public AppRepository call() throws Exception {
                        repository = new AppRepository(application);
                        return repository;
                    }
                }
        );
        try{
            return future.get();
        }catch (InterruptedException | ExecutionException e){
            Log.d("CJW", "Problem getting AppRepository, thread error.");
        }
        return null;
    }

    public RosterDAO getRosterDAO() {
        return rosterDAO;
    }


    //classroom methods
    public void createGroups(int groupSize, int classroomId){
        Classroom classroom = classroomDAO.getClassroomById(classroomId);
        classroom.setGroupSize(groupSize);
        List<Roster> roster = rosterDAO.getAllRostersByClassroomId(classroomId);
        int totalStudents = roster.size();
        int groupNum = (totalStudents + groupSize - 1) / groupSize;
        int peopleInGroups = 0;
        for(int i = 0; i<groupNum; i++){
            for(int k = 0; k < groupSize; k++){
                if(peopleInGroups == totalStudents){
                    break;
                }
                User randomKid = getRandomStudentFromClassForGroups(classroomId);
                if (randomKid == null) {
                    break;
                }
                Group group = new Group(classroomId, randomKid.getUserId(), groupSize, i);
                groupDAO.insert(group);
                peopleInGroups++;
            }
        }
        classroom.setGroupsCreated(true);
    }

    public User getRandomStudentFromClassForGroups(int classroomId) {
        List<Roster> roster = rosterDAO.getAllRostersByClassroomId(classroomId);
        List<User> availableStudents = new ArrayList<>();
        for (Roster r : roster) {
            User student = userDAO.getUserById(r.getStudentId()).getValue();
            if (student != null && !studentInGroup(student, classroomId)) {
                availableStudents.add(student);
            }
        }
        if (availableStudents.isEmpty()) {
            return null;
        }
        Random random = new Random();
        return availableStudents.get(random.nextInt(availableStudents.size()));
    }

    public boolean studentInGroup(User user, int classroomId){
        List<Group> groups = groupDAO.getAllGroupsByClassroomId(classroomId);
        List<User> kidsInGroups = new ArrayList<>();
        for(Group group : groups){
            kidsInGroups.add(userDAO.getUserById(group.getStudentId()).getValue());
        }
        if(kidsInGroups.contains(user)){
            return true;
        }
        return false;
    }

    public List<User> getClassroomStudents(int classroomId) {
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

    //user methods
    public List<Classroom> getClassrooms(int userId) {
        String role = userDAO.getUserById(userId).getValue().getRole();
        if(role == null){
            return null;
        } else if (role.equalsIgnoreCase("admin")) {
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

    public boolean joinClassroomByName(String classroomName, int userId){
        String role = userDAO.getUserById(userId).getValue().getRole();
        if(role == null){
            return false;
        } else if (!role.equals("student")) {
            return false;
        }

        Classroom classroom = classroomDAO.getClassroomByName(classroomName);
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
}
