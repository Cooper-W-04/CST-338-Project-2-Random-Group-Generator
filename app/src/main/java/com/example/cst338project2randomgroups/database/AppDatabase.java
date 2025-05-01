package com.example.cst338project2randomgroups.database;

import android.content.Context;
import android.util.Log;

import com.example.cst338project2randomgroups.database.entities.Classroom;
import com.example.cst338project2randomgroups.database.entities.Group;
import com.example.cst338project2randomgroups.database.entities.Roster;
import com.example.cst338project2randomgroups.database.entities.User;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {User.class, Classroom.class, Roster.class, Group.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public static final String USER_TABLE = "users";
    private static final String DATABASE_NAME = "AppDatabase";
    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    public static final String TEACHER_ROLE = "teacher";
    public static final String STUDENT_ROLE = "student";

    public static final String ADMIN_ROLE = "admin";


    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public static AppDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (AppDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    DATABASE_NAME
                            )
                            .fallbackToDestructiveMigration()
                            .addCallback(addDefaultValues)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            Log.i("CJW", "DATABASE CREATED!");
            databaseWriteExecutor.execute(() -> {
                UserDAO dao = INSTANCE.userDAO();
                dao.deleteAll();
                User admin = new User("admin1", "admin1", ADMIN_ROLE);
                admin.setAdmin(true);
                dao.insert(admin);


                User student1 = new User("student1", "student1", STUDENT_ROLE);
                dao.insert(student1);

                User teacher1 = new User("teacher1", "teacher1", TEACHER_ROLE);

                dao.insert(teacher1);
            });
        }
    };

    public abstract ClassroomDAO classroomDAO();

    public abstract UserDAO userDAO();

    public abstract RosterDAO rosterDAO();

    public abstract GroupDAO groupDAO();
}

