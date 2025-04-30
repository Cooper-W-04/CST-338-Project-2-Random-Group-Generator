package com.example.cst338project2randomgroups;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cst338project2randomgroups.database.AppRepository;
import com.example.cst338project2randomgroups.databinding.ActivityLoginBinding;
import com.example.cst338project2randomgroups.databinding.ActivitySignupBinding;

public class SignupActivity extends AppCompatActivity {

    private ActivitySignupBinding binding;

    private AppRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repository = AppRepository.getRepository(getApplication());
    }

    static Intent SignupActivityIntentFactory(Context context){
        return new Intent(context, SignupActivity.class);
    }
}