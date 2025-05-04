package com.example.cst338project2randomgroups;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cst338project2randomgroups.database.AppRepository;
import com.example.cst338project2randomgroups.database.entities.User;
import com.example.cst338project2randomgroups.databinding.ActivityAdminViewAllClassesBinding;
import com.example.cst338project2randomgroups.databinding.ActivityMainBinding;


public class adminViewAllClassesActivity extends AppCompatActivity {
    private ActivityAdminViewAllClassesBinding binding;
    private AppRepository repository;

    private User user;

    private int id;
    
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ActivityAdminViewAllClassesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        id = getIntent().getIntExtra("StudentId", -1);
        
        repository = AppRepository.getRepository(getApplication());

        repository.getUserById(id).observe(this, user -> {
            if (user != null){
                this.user = user;
            }
        });

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.mainActivityIntentFactory(getApplicationContext(), id);
                startActivity(intent);

            }
        });


    }

    static Intent adminAllClasssesIntentFactory(Context context, int studentId){
        Intent intent =  new Intent(context, adminViewAllClassesActivity.class);
        intent.putExtra("StudentId", studentId);
        return intent;
    }

}
