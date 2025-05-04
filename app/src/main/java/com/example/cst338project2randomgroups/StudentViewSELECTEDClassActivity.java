package com.example.cst338project2randomgroups;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cst338project2randomgroups.database.AppRepository;
import com.example.cst338project2randomgroups.database.entities.User;
import com.example.cst338project2randomgroups.databinding.ActivityStudentViewAllclassesBinding;
import com.example.cst338project2randomgroups.databinding.ActivityStudentViewSelectedclassBinding;

public class StudentViewSELECTEDClassActivity extends AppCompatActivity {

    private ActivityStudentViewSelectedclassBinding binding;
    private AppRepository repository;

    //ATJ= ann treasa jojo
    private static final String TAG = "ATJ";

    //the id of the student who clicked the "View Class" button
    private int studentId;

    //to store the student information
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentViewSelectedclassBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //recognize the student user here --> aka the passed in value through intent
        studentId = getIntent().getIntExtra("STUDENT_ID", -1);
        repository = AppRepository.getRepository(getApplication());

        //grab logged in student's information from the database
        //store it ina user object so we can use it later
        repository.getUserById(studentId).observe(this, user -> {
            if (user != null) {
                this.user = user;
            }
        });

        binding.goBackStudentLandingPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = StudentViewALLClassesActivity.studentViewALLClassesIntentFactory(getApplicationContext(), user.getUserId());
                startActivity(intent);
            }
        });

        binding.goBackStudentLandingPageButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = MainActivity.mainActivityIntentFactory(getApplicationContext(), user.getUserId());
                startActivity(intent);
                return true;
            }
        });

        binding.modifyPreferencesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = StudentPreferencesActivity.studentPreferencesIntentFactory(getApplicationContext(), user.getUserId());
                startActivity(intent);

            }
        });
    }

    public static Intent studentViewSELECTEDClassesIntentFactory(Context context, int studentId){
        Intent intent =  new Intent(context, StudentViewSELECTEDClassActivity.class);
        intent.putExtra("STUDENT_ID", studentId);
        return intent;
    }
}