package com.example.cst338project2randomgroups;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;

import com.example.cst338project2randomgroups.database.AppRepository;
import com.example.cst338project2randomgroups.database.entities.User;
import com.example.cst338project2randomgroups.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    public static final int LOGGED_OUT = -1;
    private static final String MAIN_ACTIVITY_USER_ID = "com.example.cst338project2randomgroups.MAIN_ACTIVITY_USER_ID";
    static final String SHARED_PREFERENCE_USERID_KEY = "com.example.cst338project2randomgroups.SHARED_PREFERENCE_USERID_KEY";
    static final String SAVED_INSTANCE_STATE_USERID_KEY = "com.example.cst338project2randomgroups.SAVED_INSTANCE_STATE_USERID_KEY";
    private int loggedInUserId = -1;
    User user;
    private AppRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loggedInUserId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);
        repository = AppRepository.getRepository(getApplication());
        loginUser(savedInstanceState);
        if (loggedInUserId == LOGGED_OUT) {
            Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
            startActivity(intent);
        }

        Button logOutButton = binding.logOutButton;
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutDialog();
            }
        });
    }

    private void loginUser(Bundle savedInstanceState) {
        // Check shared preference for logged in user
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_USERID_KEY, Context.MODE_PRIVATE);

        loggedInUserId = sharedPreferences.getInt(SHARED_PREFERENCE_USERID_KEY, LOGGED_OUT);
        if(loggedInUserId == LOGGED_OUT && savedInstanceState != null && savedInstanceState.containsKey(SHARED_PREFERENCE_USERID_KEY)) {
            loggedInUserId = savedInstanceState.getInt(SHARED_PREFERENCE_USERID_KEY, LOGGED_OUT);
        }
        if(loggedInUserId == LOGGED_OUT) {
            loggedInUserId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);
        }

        // Check intent for logged in user
        if(loggedInUserId == LOGGED_OUT) {
            return;
        }

        updateSharedPreference();

        LiveData<User> userObserver = repository.getUserById(loggedInUserId);
        userObserver.observe(this, user -> {
            this.user = user;
            if(user != null){
                updateAfterLogin();
            }
        });
    }

    private void updateAfterLogin(){
        invalidateOptionsMenu();
        binding.welcome.setText("Welcome "+user.getRole() + "!");

        if(user.getRole().equalsIgnoreCase("Admin")){
            binding.viewAllClasses.setVisibility(View.VISIBLE);
            binding.createAdmin.setVisibility(View.VISIBLE);
            binding.viewClasses.setVisibility(View.GONE);
            binding.createNewClassroom.setVisibility(View.GONE);
            binding.viewEnrolledClasses.setVisibility(View.GONE);
            binding.joinClassroom.setVisibility(View.GONE);
            binding.editMaxGroupSize.setVisibility(View.VISIBLE);
        } else if(user.getRole().equalsIgnoreCase("Teacher")){
            binding.viewAllClasses.setVisibility(View.GONE);
            binding.createAdmin.setVisibility(View.GONE);
            binding.viewClasses.setVisibility(View.VISIBLE);
            binding.createNewClassroom.setVisibility(View.VISIBLE);
            binding.viewEnrolledClasses.setVisibility(View.GONE);
            binding.joinClassroom.setVisibility(View.GONE);
        } else{
            binding.viewAllClasses.setVisibility(View.GONE);
            binding.createAdmin.setVisibility(View.GONE);
            binding.viewClasses.setVisibility(View.GONE);
            binding.createNewClassroom.setVisibility(View.GONE);
            binding.viewEnrolledClasses.setVisibility(View.VISIBLE);
            binding.joinClassroom.setVisibility(View.VISIBLE);
        }
    }

    private void logout() {
        loggedInUserId = LOGGED_OUT;
        updateSharedPreference();
        getIntent().putExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);
        startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
    }

    private void updateSharedPreference(){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(SHARED_PREFERENCE_USERID_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.putInt(SHARED_PREFERENCE_USERID_KEY, loggedInUserId);
        sharedPrefEditor.apply();
    }

    private void showLogoutDialog(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
        final AlertDialog alertDialog = alertBuilder.create();

        alertBuilder.setMessage("Logout?");
        alertBuilder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logout();
            }
        });
        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        alertBuilder.create().show();
    }

    static Intent mainActivityIntentFactory(Context context, int userId){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
        return intent;
    }
}