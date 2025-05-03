package com.example.cst338project2randomgroups;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cst338project2randomgroups.adapters.UserAdapter;
import com.example.cst338project2randomgroups.database.AppRepository;
import com.example.cst338project2randomgroups.databinding.ActivityAuthorityViewSelectedClassroomBinding;

public class ActivityAuthorityViewSelectedClassroom extends AppCompatActivity {
    ActivityAuthorityViewSelectedClassroomBinding binding;
    private int classroomId;
    public AppRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthorityViewSelectedClassroomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int classroomId = getIntent().getIntExtra("CLASSROOM_ID", -1);
        repository = AppRepository.getRepository(getApplication());

        repository.getClassroomStudents(classroomId).observe(this, users -> {
            UserAdapter adapter = new UserAdapter(this, users);
            binding.studentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            binding.studentsRecyclerView.setAdapter(adapter);
        });


        Button goBackButton = binding.goBackClassroomViewerButton;
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AuthorityViewClassroomsActivity.authorityViewClassroomsIntentFactory(getApplicationContext(), repository.getClassroomById(classroomId).getValue().getTeacherId());
                startActivity(intent);
            }
        });
    }

    static Intent activityAuthorityViewSelectedClassroomIntentFactory(Context context){
        Intent intent = new Intent(context, ActivityAuthorityViewSelectedClassroom.class);
        return intent;
    }
}