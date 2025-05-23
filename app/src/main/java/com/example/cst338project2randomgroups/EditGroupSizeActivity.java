package com.example.cst338project2randomgroups;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cst338project2randomgroups.database.AppRepository;
import com.example.cst338project2randomgroups.database.entities.User;
import com.example.cst338project2randomgroups.databinding.ActivityEditGroupsizeBinding;

public class EditGroupSizeActivity extends AppCompatActivity {
    private ActivityEditGroupsizeBinding binding;

    private AppRepository repository;

    private int id;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ActivityEditGroupsizeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repository = AppRepository.getRepository(getApplication());

        id = getIntent().getIntExtra("StudentId", -1);

        repository.getUserById(id).observe(this, user -> {
            if (user != null){
                this.user = user;
            }
        });

        binding.confirmNewMaxButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateMax();
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

    private void updateMax() {
        int newNum = Integer.parseInt(binding.newMax.getText().toString());
        toastMaker("Updated Max Group Size");
        binding.titleText.setText("Current max group size is " + newNum);

    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    static Intent EditGroupSizeActivityIntentFactory (Context context){
        return new Intent(context, EditGroupSizeActivity.class);
    }
}
