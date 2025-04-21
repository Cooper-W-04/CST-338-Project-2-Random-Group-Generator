package com.example.cst338project2randomgroups.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.cst338project2randomgroups.database.entities.User;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AppRepository {
    private final ClassroomDAO classroomDAO;
    private final UserDAO userDAO;
    private static AppRepository repository;

    private AppRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        this.classroomDAO = db.classroomDAO();
        this.userDAO = db.userDAO();
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
            Log.d("CJW", "Problem getting GymLogRepository, thread error.");
        }
        return null;
    }
}
