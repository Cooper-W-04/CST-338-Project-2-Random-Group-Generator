package com.example.cst338project2randomgroups;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cst338project2randomgroups.databinding.ActivityAuthorityViewSelectedClassroomBinding;

public class ActivityAuthorityViewSelectedClassroom extends AppCompatActivity {
    ActivityAuthorityViewSelectedClassroomBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthorityViewSelectedClassroomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}