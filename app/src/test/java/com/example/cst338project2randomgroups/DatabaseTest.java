//Cooper Westervelt made this :3
package com.example.cst338project2randomgroups;

import android.app.Application;

import androidx.test.core.app.ApplicationProvider;

import com.example.cst338project2randomgroups.database.AppRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class DatabaseTest {
    AppRepository repository;

    @BeforeEach
    public void setup(){
        Application application = ApplicationProvider.getApplicationContext();
        repository = AppRepository.getRepository(application);
    }

    @AfterEach
    public void teardown(){
        repository = null;
    }


}
