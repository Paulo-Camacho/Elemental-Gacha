package com.example.mydemoapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;

import com.example.mydemoapp.Database.GachaRepository;
import com.example.mydemoapp.Database.entities.User;
import com.example.mydemoapp.databinding.ActivityLoginBinding;
import com.example.mydemoapp.databinding.ActivityViewCollectionBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private GachaRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        repository = GachaRepository.getRepository(getApplication());

        binding.addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyUser();
            }
        });

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.mainActivityFactory(getApplicationContext());
                startActivity(intent);
            }
        });

    }

    private void verifyUser() {
        String username = binding.usernameInputEditText.getText().toString();
        String password = binding.passwordInputEditText.getText().toString();

        if(username.isEmpty()){
            toastMaker("Username may not be blank");
            return;
        }
        LiveData<User> userObserver = repository.getUserByUsername(username);
        userObserver.observe(this,user -> {
            if(user != null){
                if(password.equals(user.getPassword())){
                    startActivity(UserActivity.userActivityFactory(getApplicationContext(), user.getId()));
                }else{
                    toastMaker("Invalid password.");
                    binding.passwordInputEditText.setSelection(0);
                }
            }else{
                toastMaker(String.format("%s is not a valid username.",username));
                binding.usernameInputEditText.setSelection(0);
            }
        });
    }

    private void toastMaker(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    static Intent loginIntentFactory(Context context){
        return new Intent(context, LoginActivity.class);
    }


}