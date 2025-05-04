package com.example.cst338project2randomgroups;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cst338project2randomgroups.adapters.ClassroomAdapter;
import com.example.cst338project2randomgroups.database.AppRepository;
import com.example.cst338project2randomgroups.database.entities.Classroom;
import com.example.cst338project2randomgroups.databinding.ActivityAuthorityViewClassroomsBinding;

import java.util.List;

public class AuthorityViewClassroomsActivity extends AppCompatActivity {

    ActivityAuthorityViewClassroomsBinding binding;
    private int authId;
    AppRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthorityViewClassroomsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = AppRepository.getRepository(getApplication());
        authId = getIntent().getIntExtra("AUTH_ID", -1);

        binding.classroomRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        LiveData<List<Classroom>> classroomLiveData = repository.getClassrooms(authId);

        ClassroomAdapter adapter = new ClassroomAdapter(this, classroomLiveData, this, true);
        binding.classroomRecyclerView.setAdapter(adapter);

        binding.goBackToLandingPageButton.setOnClickListener(v -> finish());

        repository.getUserById(authId).observe(this, user -> {
            if (user != null && user.isAdmin()) {
                binding.createClassroomButton.setVisibility(View.GONE);
            } else {
                binding.createClassroomButton.setVisibility(View.GONE); //changed this to gone
            }
        });
        //TODO:
//        binding.createClassroomButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = CreateClass.CreateClassIntentFactory(getApplicationContext());
//                startActivity(intent);
//            }
//        });
    }

    static Intent authorityViewClassroomsIntentFactory(Context context, int authId) {
        Intent intent = new Intent(context, AuthorityViewClassroomsActivity.class);
        intent.putExtra("AUTH_ID", authId);
        return intent;
    }
}
