package com.example.cst338project2randomgroups;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.cst338project2randomgroups.database.AppDatabase;
import com.example.cst338project2randomgroups.database.AppRepository;
import com.example.cst338project2randomgroups.database.entities.User;
import com.example.cst338project2randomgroups.databinding.ActivityAddAdminBinding;
import com.example.cst338project2randomgroups.databinding.ActivitySignupBinding;

public class AddAdminActivity extends AppCompatActivity {

    private ActivityAddAdminBinding binding;

    private AppRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityAddAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repository = AppRepository.getRepository(getApplication());

        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyAdmin();
            }
        });
    }

    private void verifyAdmin() {
        String usernameInput = binding.adminUser.getText().toString();
        String passwordInput = binding.adminPassword.getText().toString();
        String role = AppDatabase.ADMIN_ROLE;

        if(usernameInput.isEmpty() | passwordInput.isEmpty()){
            toastMaker("Fields can not be empty");
            return;
        }
        LiveData<User> observer = repository.getUserByUsername(usernameInput);
        observer.observe(this, user -> {
            if(user != null){
                toastMaker("This admin already exists");
            }
            addUser(usernameInput, passwordInput, role);
        });
    }

    private void addUser(String usernameInput, String passwordInput, String role) {
        User newAdmin = new User(usernameInput,passwordInput,role);
        Log.v("MSW", Integer.toString(newAdmin.getUserId()));
        toastMaker(String.format("Added %s", usernameInput));
        AppDatabase.databaseWriteExecutor.execute(()->{
            AppDatabase dtbs = AppDatabase.getDatabase(this);
            dtbs.userDAO().insert(newAdmin);
            startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), newAdmin.getUserId()));

        });

    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    static Intent AddAdminActivityIntentFactory (Context context){
        return new Intent(context, AddAdminActivity.class);
    }
}
