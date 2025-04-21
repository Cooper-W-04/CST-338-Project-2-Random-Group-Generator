package com.example.cst338project2randomgroups;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity {

//    private ActivityLoginBinding binding;
//
//    private GymLogRepository repository;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    private void verifyUser(){
//        String username = binding.userNameLoginEditText.getText().toString();
//        if(username.isEmpty()){
//            toastMaker("Username cannot be blank");
//            return;
//        }
//
//        LiveData<User> userObserver = repository.getUserByUsername(username);
//        userObserver.observe(this, user -> {
//            if(user != null){
//                String password = binding.passwordLoginEditText.getText().toString();
//                if(password.equals(user.getPassword())){
//                    startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), user.getId()));
//                } else{
//                    toastMaker("Invalid password.");
//                    binding.passwordLoginEditText.setSelection(0);
//                }
//            } else{
//                toastMaker(String.format("%s is not a valid username", username));
//                binding.userNameLoginEditText.setSelection(0);
//            }
//        });
    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    static Intent loginIntentFactory(Context context){
        return new Intent(context, LoginActivity.class);
    }
}