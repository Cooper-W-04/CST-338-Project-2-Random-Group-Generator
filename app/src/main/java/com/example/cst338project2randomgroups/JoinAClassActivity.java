package com.example.cst338project2randomgroups;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cst338project2randomgroups.database.AppRepository;
import com.example.cst338project2randomgroups.database.entities.User;
import com.example.cst338project2randomgroups.databinding.ActivityJoinAclassBinding;

public class JoinAClassActivity extends AppCompatActivity {
    //so i can access user input
    private ActivityJoinAclassBinding binding;

    //so i can access database
    private AppRepository repository;

    //ATJ= ann treasa jojo
    private static final String TAG = "ATJ";


    //the class the student wants to join
    private int classID;

    private String className;

    //the id of the student who clicked the "Join A Classroom" button
    private int studentId;

    //to store the student information
    private User user;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJoinAclassBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //recognize the student user here --> aka the passed in value through intent
        studentId = getIntent().getIntExtra("STUDENT_ID", -1);
        repository = AppRepository.getRepository(getApplication());

        //grab logged in student's information from the database
        //store it ina user object so we can use it later
        repository.getUserById(studentId).observe(this, user -> {
            if (user != null) {
                this.user = user;
            }
        });

        binding.joinAClassAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getClassroomIdFromEditText();
                addStudentToClass(); 

            }
        });

        binding.goBackStudentLandingPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.mainActivityIntentFactory(getApplicationContext(), user.getUserId());
                startActivity(intent);
            }
        });

    }

    //ToDo: Ensure the student id entered is stored in the roster table.
    // ToDo: Ensure the joinClassroom() method inside User.java is functioning

    //As of right now Sunday April 27, this method is ALWAYS returning false with the toast message "unable to join"
    private void addStudentToClass() {
        //since we are working with live data, we must check if user is a valid object otherwise we will get null pointer exception
        if(user != null){
            //repository.joinClassroomByName(className, user.getUserId());
            boolean isAdded = repository.joinClassroomById(classID, user);

            if(isAdded){
                Toast.makeText(this, user.getUsername() + " has successfully joined the class!", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, user.getUsername() + " has already enrolled or unable to join!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    //when join a classroom page opens up, we have the logged in students ID as that is being passed in
    //the "Join A Class" button is clicked
    static Intent joinAClassIntentFactory(Context context, int studentId) {
        Intent intent =  new Intent(context, JoinAClassActivity.class);
        intent.putExtra("STUDENT_ID", studentId);
        return intent;
    }

    static Intent joinAClassIntentFactory(Context context) {
        return new Intent(context, JoinAClassActivity.class);
    }



    private void getClassroomIdFromEditText() {
        //need to put in a try catch because when the app runs it will try to parse a empty string into a integer
        // this is because when the app opens up to this page, user has not yet entered anything
        try{
            className = binding.joinClassroomClassIDInputEditText.getText().toString();
            classID = Integer.parseInt(binding.joinClassroomClassIDInputEditText.getText().toString());
        } catch (NumberFormatException e) {
            Log.d(TAG, "Error reading value from class id edit text.");
            throw new RuntimeException(e);
        }
    }
}