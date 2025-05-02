package com.example.cst338project2randomgroups;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cst338project2randomgroups.database.AppRepository;
import com.example.cst338project2randomgroups.databinding.ActivityEditGroupsizeBinding;

public class EditGroupSizeActivity extends AppCompatActivity {
    private ActivityEditGroupsizeBinding binding;

    private AppRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ActivityEditGroupsizeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repository = AppRepository.getRepository(getApplication());

        binding.confirmNewMaxButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateMax();
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

}
