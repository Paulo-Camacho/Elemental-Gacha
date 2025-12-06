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

        // LOGIN BUTTON
        binding.addUserButton.setOnClickListener(v -> verifyUser());

        // BACK BUTTON
        binding.backButton.setOnClickListener(v -> {
            startActivity(MainActivity.mainActivityFactory(getApplicationContext()));
            finish();
        });
    }

    private void verifyUser() {
        String username = binding.usernameInputEditText.getText().toString().trim();
        String password = binding.passwordInputEditText.getText().toString().trim();

        if (username.isEmpty()) {
            toast("Username may not be blank.");
            return;
        }

        LiveData<User> userObserver = repository.getUserByUsername(username);

        userObserver.observe(this, user -> {

            if (user == null) {
                toast(username + " is not a valid username.");
                binding.usernameInputEditText.requestFocus();
                return;
            }

            if (!password.equals(user.getPassword())) {
                toast("Invalid password.");
                binding.passwordInputEditText.requestFocus();
                return;
            }

            // SUCCESSFUL LOGIN
            saveLoggedInUser(user.getUserID());
            toast("Login successful!");

            if (user.getIsPremium() && !user.getIsAdmin()) {
                // PREMIUM
                Intent intent = PremiumUserLandingPageActivity
                        .premiumUserIntentFactory(getApplicationContext(), user.getUserID());
                startActivity(intent);
            } else {
                // NORMAL USER OR ADMIN
                Intent intent = UserActivity.userActivityFactory(
                        getApplicationContext(),
                        user.getUserID()
                );
                startActivity(intent);
            }

            finish();
        });
    }

    private void saveLoggedInUser(int userId) {
        SharedPreferences sharedPreferences = getSharedPreferences(
                getString(R.string.preference_file_key),
                MODE_PRIVATE
        );

        sharedPreferences.edit()
                .putInt(getString(R.string.preference_userId_key), userId)
                .apply();
    }

    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    static Intent loginIntentFactory(Context context) {
        return new Intent(context, LoginActivity.class);
    }
}
