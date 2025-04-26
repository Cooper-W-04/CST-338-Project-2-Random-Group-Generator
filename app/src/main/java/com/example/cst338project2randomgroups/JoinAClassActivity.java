package com.example.cst338project2randomgroups;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cst338project2randomgroups.database.AppRepository;
import com.example.cst338project2randomgroups.databinding.ActivityJoinAclassBinding;

public class JoinAClassActivity extends AppCompatActivity {
    //so i can access user input
    private ActivityJoinAclassBinding binding;

    //so i can access database
    private AppRepository repository;

    //ATJ= ann treasa jojo
    private static final String TAG = "ATJ";


    int classID;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJoinAclassBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.joinAClassAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getClassroomIdFromEditText();

            }
        });

    }

    static Intent joinAClassIntentFactory(Context context) {
        return new Intent(context, JoinAClassActivity.class);
    }



    private void getClassroomIdFromEditText() {
        //need to put in a try catch because when the app runs it will try to parse a empty string into a integer
        // this is because when the app opens up to this page, user has not yet entered anything
        try{
            classID = Integer.parseInt(binding.joinClassroomClassIDInputEditText.getText().toString());
            Toast.makeText(this, "Got entered ID: " + classID, Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException e) {
            Log.d(TAG, "Error reading value from class id edit text.");
            throw new RuntimeException(e);
        }

    }
}