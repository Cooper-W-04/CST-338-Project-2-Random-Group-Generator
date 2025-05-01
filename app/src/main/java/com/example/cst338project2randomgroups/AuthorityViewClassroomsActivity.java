package com.example.cst338project2randomgroups;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cst338project2randomgroups.database.AppRepository;
import com.example.cst338project2randomgroups.database.entities.Classroom;
import com.example.cst338project2randomgroups.databinding.ActivityAuthorityViewClassroomsBinding;

import java.util.List;

public class AuthorityViewClassroomsActivity extends AppCompatActivity {
    ActivityAuthorityViewClassroomsBinding binding;
    private int authId;
    List<Classroom> authClassrooms;
    AppRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthorityViewClassroomsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = AppRepository.getRepository(getApplication());

        authId = getIntent().getIntExtra("AUTH_ID", -1);
        repository.getClassrooms(authId).observe(this, classrooms -> {
            if (classrooms != null) {
                authClassrooms = classrooms;
                // TODO: Update UI with classrooms (e.g., populate RecyclerView)
            }
        });
    }

    static Intent authorityViewClassroomsIntentFactory(Context context, int authId){
        Intent intent = new Intent(context, AuthorityViewClassroomsActivity.class);
        intent.putExtra("AUTH_ID", authId);
        return intent;
    }
}