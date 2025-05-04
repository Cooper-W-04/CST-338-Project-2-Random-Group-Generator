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
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

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
    public void createGroups(int groupSize, int classroomId) {
        classroomDAO.getClassroomById(classroomId).observeForever(classroom -> {
            if (classroom == null) return;
            classroom.setGroupSize(groupSize);
            rosterDAO.getAllRostersByClassroomId(classroomId).observeForever(rosters -> {
                if (rosters == null || rosters.isEmpty()) return;

                int totalStudents = rosters.size();
                int groupNum = (totalStudents + groupSize - 1) / groupSize;
                List<Integer> studentIdsAdded = new ArrayList<>();
                Random random = new Random();

                for (int i = 0; i < groupNum; i++) {
                    final int groupNumber = i; // copy for lambda
                    for (int k = 0; k < groupSize; k++) {
                        getRandomStudentFromClassForGroups(classroomId, studentIdsAdded).observeForever(randomStudent -> {
                            if (randomStudent == null) return;
                            Group group = new Group(classroomId, randomStudent.getUserId(), groupSize, groupNumber);
                            groupDAO.insert(group);
                            studentIdsAdded.add(randomStudent.getUserId());
                        });
                    }
                }

                classroom.setGroupsCreated(true);
                classroomDAO.updateClassroom(classroom);
            });
        });
    }


    public LiveData<User> getRandomStudentFromClassForGroups(int classroomId, List<Integer> alreadyUsedIds) {
        MediatorLiveData<User> result = new MediatorLiveData<>();

        rosterDAO.getAllRostersByClassroomId(classroomId).observeForever(rosters -> {
            if (rosters == null) {
                result.setValue(null);
                return;
            }

            List<User> validStudents = new ArrayList<>();
            List<LiveData<User>> userLiveDataList = new ArrayList<>();

            for (Roster r : rosters) {
                userLiveDataList.add(userDAO.getUserById(r.getStudentId()));
            }

            MediatorLiveData<List<User>> studentsCollector = new MediatorLiveData<>();
            List<User> collected = new ArrayList<>();
            AtomicInteger count = new AtomicInteger();

            for (LiveData<User> userLive : userLiveDataList) {
                studentsCollector.addSource(userLive, user -> {
                    if (user != null && !alreadyUsedIds.contains(user.getUserId())) {
                        collected.add(user);
                    }
                    if (count.incrementAndGet() == userLiveDataList.size()) {
                        if (collected.isEmpty()) {
                            result.setValue(null);
                        } else {
                            Random random = new Random();
                            result.setValue(collected.get(random.nextInt(collected.size())));
                        }
                    }
                });
            }
        });

        return result;
    }


    public LiveData<Boolean> studentInGroup(User user, int classroomId) {
        MediatorLiveData<Boolean> result = new MediatorLiveData<>();

        groupDAO.getAllGroupsByClassroomId(classroomId).observeForever(groups -> {
            if (groups != null) {
                for (Group group : groups) {
                    if (group.getStudentId() == user.getUserId()) {
                        result.setValue(true);
                        return;
                    }
                }
            }
            result.setValue(false);
        });

        return result;
    }

    public LiveData<List<User>> getClassroomStudents(int classroomId) {
        MediatorLiveData<List<User>> result = new MediatorLiveData<>();

        rosterDAO.getAllRosters().observeForever(rosters -> {
            if (rosters == null) return;

            List<Integer> studentIds = new ArrayList<>();
            for (Roster r : rosters) {
                if (r.getClassroomId() == classroomId) {
                    studentIds.add(r.getStudentId());
                }
            }

            if (studentIds.isEmpty()) {
                result.setValue(Collections.emptyList());
                return;
            }

            List<User> collected = new ArrayList<>();
            int total = studentIds.size();

            for (int id : studentIds) {
                userDAO.getUserById(id).observeForever(user -> {
                    if (user != null && "student".equalsIgnoreCase(user.getRole())) {
                        collected.add(user);
                    }

                    if (collected.size() + (total - studentIds.size()) == total) {
                        result.setValue(new ArrayList<>(collected));
                    }
                });
            }
        });

        return result;
    }


    public void joinClassroomByName(String classroomName, int userId) {
        userDAO.getUserById(userId).observeForever(user -> {
            if (user == null || !"student".equalsIgnoreCase(user.getRole())) return;

            classroomDAO.getClassroomByName(classroomName).observeForever(classroom -> {
                if (classroom == null) return;

                int classroomId = classroom.getClassroomId();
                rosterDAO.getAllRostersByClassroomId(classroomId).observeForever(rosters -> {
                    for (Roster r : rosters) {
                        if (r.getStudentId() == userId) return;
                    }

                    Roster roster = new Roster(userId, classroomId);
                    rosterDAO.insert(roster);
                });
            });
        });
    }

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

    public LiveData<Classroom> getClassroomById(int classroomId){
        return classroomDAO.getClassroomById(classroomId);
    }

    public boolean joinClassroomById(int classroomId, User user){
        //checks only students are joining the class
        if(user.getRole().equals("teacher") || user.getRole().equals("admin")){
            return false;
        }

        //classroomId does not exist
//        LiveData<Classroom> classroom = classroomDAO.getClassroomById(classroomId);
//        if(classroom == null){
//            return false;
//        }

        //check if student is already in that classroom
        //here we are getting the roster list of a specific classroom
//        List<Roster> rosterList = rosterDAO.getAllRostersByClassroomIdNow(classroomId);
//        for (Roster roster : rosterList){
//            if(roster.getStudentId() == user.getUserId()){
//                return false;
//            }
//        }

        Roster newRoster = new Roster(classroomId, user.getUserId());
        AppDatabase.databaseWriteExecutor.execute(() -> {
            rosterDAO.insert(newRoster);
        });
        return true;
    }

    public LiveData<List<Classroom>> getStudentClassrooms(int studentId) {
        return classroomDAO.getClassroomsForStudent(studentId);
    }





}
