package com.example.cst338project2randomgroups.database;

import com.example.cst338project2randomgroups.database.entities.Classroom;
import com.example.cst338project2randomgroups.database.entities.User;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {User.class, Classroom.class}, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

}
