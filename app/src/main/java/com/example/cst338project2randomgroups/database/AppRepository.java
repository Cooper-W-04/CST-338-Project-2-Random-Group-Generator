package com.example.cst338project2randomgroups.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.cst338project2randomgroups.database.entities.Classroom;
import com.example.cst338project2randomgroups.database.entities.Roster;
import com.example.cst338project2randomgroups.database.entities.User;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

//making changes
public class AppRepository {
    private final ClassroomDAO classroomDAO;
    private final UserDAO userDAO;
    private final RosterDAO rosterDAO;
    private static AppRepository repository;

    private AppRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        this.classroomDAO = db.classroomDAO();
        this.userDAO = db.userDAO();
        this.rosterDAO = db.rosterDAO();
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

    public ClassroomDAO getClassroomDAO(){
        return classroomDAO;
    }

    public boolean joinClassroomById(int classroomId, User user) {
        //checks only student are joining the class
        if (user.getRole().equals("teacher") || user.getRole().equals("admin") ) {
            return false;
        }

//        //classroom does not exist
        Classroom classroom = classroomDAO.getClassroomById(classroomId);
        if (classroom == null) {
            return false;
        }

        //check if student is already in class
        List<Roster> rostersInClass = rosterDAO.getAllRostersByClassroomId(classroomId);
        for (Roster roster : rostersInClass) {
            if (roster.getStudentId() == user.getUserId()) {
                return false;
            }
        }

        //student who entered valid class id
        Roster newRoster = new Roster(classroomId, user.getUserId());
        AppDatabase.databaseWriteExecutor.execute(() -> {
            Log.d("RosterInsert", "Adding userId: " + user.getUserId() + " to classId: " + classroomId);
            rosterDAO.insert(newRoster);
        });
        return true;

    }

}
