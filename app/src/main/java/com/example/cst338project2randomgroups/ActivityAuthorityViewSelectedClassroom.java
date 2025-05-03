package com.example.cst338project2randomgroups;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

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

    }
}