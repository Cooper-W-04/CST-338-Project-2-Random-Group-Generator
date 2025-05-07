package com.example.cst338project2randomgroups;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cst338project2randomgroups.adapters.ClassroomAdapter;
import com.example.cst338project2randomgroups.database.AppRepository;
import com.example.cst338project2randomgroups.database.entities.Classroom;
import com.example.cst338project2randomgroups.database.entities.User;
import com.example.cst338project2randomgroups.databinding.ActivityStudentViewAllclassesBinding;

import java.util.List;

public class StudentViewALLClassesActivity extends AppCompatActivity {

    private ActivityStudentViewAllclassesBinding binding;
    private AppRepository repository;

    //ATJ= ann treasa jojo
    private static final String TAG = "ATJ";

    //the id of the student who clicked the "View All Classrooms" button
    private int studentId;

    //to store the student information
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentViewAllclassesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //recognize the student user here --> aka the passed in value through intent
        studentId = getIntent().getIntExtra("STUDENT_ID", -1);
        repository = AppRepository.getRepository(getApplication());


        //using recycler view here
        binding.studentClassRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        //grab logged in student's information from the database
        //store it ina user object so we can use it later
        repository.getUserById(studentId).observe(this, user -> {
            if (user != null) {
                this.user = user;
                binding.titleViewALLClassesTextView.setText("All Classes for " + user.getUsername());

                //grab all the classes a student is enrolled in
                LiveData<List<Classroom>> classroomLiveData = repository.getStudentClassrooms(studentId);
                ClassroomAdapter adapter = new ClassroomAdapter(this, classroomLiveData, this, false);
                binding.studentClassRecyclerView.setAdapter(adapter);

            }
        });

        binding.goBackStudentLandingPageButton.setOnClickListener(v -> {
            Intent intent = MainActivity.mainActivityIntentFactory(getApplicationContext(), user.getUserId());
            startActivity(intent);
        });

        //switching to recycler view

//        binding.studentViewClass1Button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = StudentViewSELECTEDClassActivity.studentViewSELECTEDClassesIntentFactory(getApplicationContext(), user.getUserId());
//                startActivity(intent);
//            }
//        });
//
//        binding.studentViewClass2Button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = StudentViewSELECTEDClassActivity.studentViewSELECTEDClassesIntentFactory(getApplicationContext(), user.getUserId());
//                startActivity(intent);
//            }
//        });
//
//        binding.studentViewClass3Button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = StudentViewSELECTEDClassActivity.studentViewSELECTEDClassesIntentFactory(getApplicationContext(), user.getUserId());
//                startActivity(intent);
//            }
//        });



    }

    static Intent studentViewALLClassesIntentFactory(Context context, int studentId){
        Intent intent =  new Intent(context, StudentViewALLClassesActivity.class);
        intent.putExtra("STUDENT_ID", studentId);
        return intent;
    }

    static Intent studentViewALLClassesIntentFactory(Context context){
        return  new Intent(context, StudentViewALLClassesActivity.class);
    }
}