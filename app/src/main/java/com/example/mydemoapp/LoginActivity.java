package com.example.mydemoapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.mydemoapp.Database.GachaRepository;
import com.example.mydemoapp.Database.entities.User;
import com.example.mydemoapp.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private GachaRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = GachaRepository.getRepository(getApplication());

        // LOGIN button
        binding.addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyUser();
            }
        });

        // Return to main menu button
        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.mainActivityFactory(getApplicationContext());
                startActivity(intent);
                finish();
            }
        });
    }

    private void verifyUser() {
        String username = binding.usernameInputEditText.getText().toString().trim();
        String password = binding.passwordInputEditText.getText().toString().trim();

        if (username.isEmpty()) {
            toastMaker("Username may not be blank.");
            return;
        }

        LiveData<User> userObserver = repository.getUserByUsername(username);

        userObserver.observe(this, user -> {
            if (user != null) {

                if (password.equals(user.getPassword())) {

                    saveLoggedInUser(user.getId());

                    toastMaker("Login successful!");

                    Intent intent = UserActivity.userActivityFactory(
                            getApplicationContext(),
                            user.getId()
                    );
                    startActivity(intent);
                    finish();
                } else {
                    toastMaker("Invalid password.");
                    binding.passwordInputEditText.requestFocus();
                }

            } else {
                toastMaker(username + " is not a valid username.");
                binding.usernameInputEditText.requestFocus();
            }
        });
    }

    private void saveLoggedInUser(int userId) {
        SharedPreferences sharedPreferences = getApplicationContext()
                .getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(getString(R.string.preference_userId_key), userId);
        editor.apply();
    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    static Intent loginIntentFactory(Context context) {
        return new Intent(context, LoginActivity.class);
    }
}
