package com.example.cst338project2randomgroups;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cst338project2randomgroups.database.AppRepository;
import com.example.cst338project2randomgroups.databinding.ActivityAdminViewAllClassesBinding;
import com.example.cst338project2randomgroups.databinding.ActivityMainBinding;


public class adminViewAllClassesActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private AppRepository repository;

    
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        repository = AppRepository.getRepository(getApplication());

        binding.viewAllClasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    static Intent adminAllClasssesIntentFactory(Context context){
        return new Intent(context, adminViewAllClassesActivity.class);
    }

}
