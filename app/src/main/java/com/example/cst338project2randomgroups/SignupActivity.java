package com.example.cst338project2randomgroups;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;

import com.example.cst338project2randomgroups.database.AppDatabase;
import com.example.cst338project2randomgroups.database.AppRepository;
import com.example.cst338project2randomgroups.database.UserDAO;
import com.example.cst338project2randomgroups.database.entities.User;
import com.example.cst338project2randomgroups.databinding.ActivityLoginBinding;
import com.example.cst338project2randomgroups.databinding.ActivitySignupBinding;

public class SignupActivity extends AppCompatActivity {

    private static volatile AppDatabase INSTANCE;

    private ActivitySignupBinding binding;

    private AppRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repository = AppRepository.getRepository(getApplication());


        binding.signUpAsStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.UserRoleChoiceDisplay.setText(AppDatabase.STUDENT_ROLE);
            }
        });

        binding.signUpAsTeacherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.UserRoleChoiceDisplay.setText(AppDatabase.TEACHER_ROLE);
            }
        });

        binding.confirmSignUpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                verifySignupInputs();
            }
        });
    }

    static Intent SignupActivityIntentFactory(Context context){
        return new Intent(context, SignupActivity.class);
    }



    private void verifySignupInputs(){
        String usernameInputText = binding.newUsernameEditText.getText().toString();
        String passWordInputText = binding.newPasswordEditText.getText().toString();
        String confirmPassWordInputText = binding.confirmNewPasswordEditText.getText().toString();
        String role = binding.UserRoleChoiceDisplay.getText().toString();
        if(usernameInputText.isEmpty() || passWordInputText.isEmpty() || confirmPassWordInputText.isEmpty() || role.isEmpty()){
            toastMaker("No fields may be empty!");
            return;
        }
        LiveData<User> userObserver = repository.getUserByUsername(usernameInputText);
        userObserver.observe(this,user -> {
            if(user!=null){
                toastMaker("Username already exists, please choose other");
            }else if(!confirmPassWordInputText.equals(passWordInputText)){
                toastMaker("Password don't match, please try again");
            }else{
                addUser(usernameInputText,passWordInputText,role);
            }
        });

    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void addUser(String username,String password, String role){
        User newUser = new User(username, password, role);
        //dao.insert(newUser);
        Log.v("RHH",Integer.toString(newUser.getUserId()));
        toastMaker(String.format("Welcome %s",username));
        AppDatabase.databaseWriteExecutor.execute(()->{
           AppDatabase db = AppDatabase.getDatabase(this);
           db.userDAO().insert(newUser);
           startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), newUser.getUserId()));
        });
    }
}