package com.example.cst338project2randomgroups.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

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
    public LiveData<List<Classroom>> getClassrooms(int userId) {
        MediatorLiveData<List<Classroom>> result = new MediatorLiveData<>();

        LiveData<User> userLiveData = userDAO.getUserById(userId);
        result.addSource(userLiveData, user -> {
            if (user == null || user.getRole() == null) {
                result.setValue(new ArrayList<>());
                return;
            }

            String role = user.getRole();
            if (role.equalsIgnoreCase("admin")) {
                result.addSource(classroomDAO.getAllClassrooms(), result::setValue);
            } else if (role.equalsIgnoreCase("teacher")) {
                result.addSource(classroomDAO.getClassroomsByTeacherId(userId), result::setValue);
            } else if (role.equalsIgnoreCase("student")) {
                LiveData<List<Roster>> rosterLiveData = rosterDAO.getAllRosters();
                result.addSource(rosterLiveData, rosters -> {
                    List<Integer> classIds = new ArrayList<>();
                    for (Roster roster : rosters) {
                        if (roster.getStudentId() == userId) {
                            classIds.add(roster.getClassroomId());
                        }
                    }

                    if (classIds.isEmpty()) {
                        result.setValue(new ArrayList<>());
                    } else {
                        // Optionally create this method if not yet in DAO
                        LiveData<List<Classroom>> studentClasses = classroomDAO.getClassroomsByIds(classIds);
                        result.addSource(studentClasses, result::setValue);
                    }
                });
            } else {
                result.setValue(new ArrayList<>());
            }
        });

        return result;
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
