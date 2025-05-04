package com.example.cst338project2randomgroups;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cst338project2randomgroups.database.AppRepository;
import com.example.cst338project2randomgroups.database.entities.Classroom;
import com.example.cst338project2randomgroups.database.entities.User;
import com.example.cst338project2randomgroups.databinding.ActivityCreateClassBinding;

public class CreateClassActivity extends AppCompatActivity {
    ActivityCreateClassBinding binding;
    AppRepository repository;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateClassBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        int teacherID = getIntent().getIntExtra("USER_ID", -1);
        //Log.v("RHH",Integer.toString(currentUserID));
        //LiveData<List<Classroom>> classroomLiveData = repository.getClassrooms(authId);

        repository = AppRepository.getRepository(getApplication());

        repository.getUserById(teacherID).observe(this, user -> {
            if (user != null) {
                this.user = user;
            }
        });

        binding.CreateClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stringInput = binding.SubjectEditText.getText().toString();
                Classroom newClass = new Classroom(teacherID,stringInput);
                repository.insertClassroom(newClass);
                Toast.makeText(CreateClassActivity.this, String.format("You have created %s",stringInput), Toast.LENGTH_SHORT).show();
            }
        });

        binding.GoBackToMainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(),teacherID));
            }
        });
    }
//TODO: should check if classroom with teacher userID and classroom name already exists
//    private boolean validClassroom(String className,int userId){
//        LiveData<List<Classroom>> teacherClasses = repository.getClassrooms(userId);
//        teacherClasses.observe(this, new Observer<List<Classroom>>() {
//            @Override
//            public void onChanged(List<Classroom> classrooms) {
//                // Now you can iterate through the classrooms list
//                for(Classroom classroom : classrooms) {
//                    if(className.equals(classroom.getClassName())){
//                        return false;
//                    }
//                    // or do whatever logic you need
//                }
//            }
//        });
//
//    }

    static Intent CreateClassIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, CreateClassActivity.class);
        intent.putExtra("USER_ID", userId);
        return intent;
    }

}