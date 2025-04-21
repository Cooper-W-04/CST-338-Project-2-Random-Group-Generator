package com.example.cst338project2randomgroups;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;

import com.example.cst338project2randomgroups.database.AppRepository;
import com.example.cst338project2randomgroups.database.entities.User;
import com.example.cst338project2randomgroups.databinding.ActivityMainBinding;

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

        LiveData<User> userObserver = repository.getUserById(loggedInUserId);
        userObserver.observe(this, user -> {
            this.user = user;
            if(user != null){
                invalidateOptionsMenu();
            }
        });
    }

    static Intent mainActivityIntentFactory(Context context, int userId){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
        return intent;
    }
}