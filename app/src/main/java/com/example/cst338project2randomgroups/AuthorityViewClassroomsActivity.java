package com.example.cst338project2randomgroups;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.cst338project2randomgroups.database.AppRepository;
import com.example.cst338project2randomgroups.database.entities.Classroom;
import com.example.cst338project2randomgroups.databinding.ActivityAuthorityViewClassroomsBinding;

import java.util.List;

public class AuthorityViewClassroomsActivity extends AppCompatActivity {
    ActivityAuthorityViewClassroomsBinding binding;
    private int authId;
    LiveData<List<Classroom>> authClassrooms;
    AppRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthorityViewClassroomsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = AppRepository.getRepository(getApplication());
        authClassrooms = repository.getClassrooms(authId);
    }

    static Intent authorityViewClassroomsIntentFactory(Context context, int authId){
        Intent intent = new Intent(context, AuthorityViewClassroomsActivity.class);
        intent.putExtra("AUTH_ID", authId);
        return intent;
    }
}