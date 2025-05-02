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

//ANN WAS HERE & I ADDED BACK THE ADDITIONAL STUDENT TEACHER CLASSROOM + ROSTERS.
//IS UP TO DATE WITH MAIN. SENDING PULL REQUEST. SHOULD HAVE 0 MERGE CONFLICT
@Database(entities = {User.class, Classroom.class, Roster.class, Group.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public static final String USER_TABLE = "users";
    private static final String DATABASE_NAME = "AppDatabase";
    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    public static final String ADMIN_ROLE = "admin";

    public static final String TEACHER_ROLE = "teacher";
    public static final String STUDENT_ROLE = "student";


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
                UserDAO userDao = INSTANCE.userDAO();
                ClassroomDAO classroomDao = INSTANCE.classroomDAO();
                RosterDAO rosterDao = INSTANCE.rosterDAO();

                userDao.deleteAll();

                //default users
                User admin = new User("admin1", "admin1", "Admin");
                admin.setAdmin(true);
                userDao.insert(admin);

                User student1 = new User("student1", "student1", "Student");
                long student1Id = userDao.insert(student1);

                User student2 = new User("student2", "student2", "Student");
                long student2Id = userDao.insert(student2);

                User student3 = new User("student3", "student3", "Student");
                long student3Id = userDao.insert(student3);

                User student4 = new User("student4", "student4", "Student");
                long student4Id = userDao.insert(student4);

                User student5 = new User("student5", "student5", "Student");
                long student5Id = userDao.insert(student5);

                User teacher1 = new User("teacher1", "teacher1", TEACHER_ROLE);
                long teacher1Id = userDao.insert(teacher1);

                User teacher2 = new User("teacher2", "teacher2", "Teacher");
                long teacher2Id = userDao.insert(teacher2);

                User teacher3 = new User("teacher3", "teacher3", "Teacher");
                long teacher3Id = userDao.insert(teacher3);

                //default classrooms
                Classroom englishClass = new Classroom((int)teacher1Id, "English 200");
                int englishClassId = (int)classroomDao.insert(englishClass);

                Classroom mathClass = new Classroom((int)teacher2Id, "Math 150");
                int mathClassId = (int)classroomDao.insert(mathClass);

                Classroom scienceClass = new Classroom((int)teacher3Id, "Science 100");
                int scienceClassId = (int)classroomDao.insert(scienceClass);

                //default rosters
                Roster roster1 = new Roster(mathClassId, (int)student1Id);  // student1 in math class
                Roster roster2 = new Roster(englishClassId, (int)student1Id); // student1 in english class
                Roster roster3 = new Roster(scienceClassId, (int)student1Id); // student1 in science class


                Roster roster4 = new Roster(mathClassId, (int)student2Id);   // student2 in math class
                Roster roster5 = new Roster(englishClassId, (int)student2Id);   // student2 in english class

                Roster roster6 = new Roster(mathClassId, (int)student3Id);  // student3 in math class
                Roster roster7 = new Roster(scienceClassId, (int)student3Id);   // student3 in science class

                Roster roster8 = new Roster(mathClassId, (int)student4Id);  // student4 in math class
                Roster roster9 = new Roster(englishClassId, (int)student4Id);   // student4 in english class

                Roster roster10 = new Roster(mathClassId, (int)student5Id);  // student5 in math class


                rosterDao.insert(roster1);
                rosterDao.insert(roster2);
                rosterDao.insert(roster3);
                rosterDao.insert(roster4);
                rosterDao.insert(roster5);
                rosterDao.insert(roster6);
                rosterDao.insert(roster7);
                rosterDao.insert(roster8);
                rosterDao.insert(roster9);
                rosterDao.insert(roster10);

            });
        }
    };

    public abstract ClassroomDAO classroomDAO();

    public abstract UserDAO userDAO();

    public abstract RosterDAO rosterDAO();

    public abstract GroupDAO groupDAO();
}
