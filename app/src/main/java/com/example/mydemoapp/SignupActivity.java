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
import com.example.mydemoapp.databinding.ActivitySignupBinding;

public class SignupActivity extends AppCompatActivity {

    private ActivitySignupBinding binding;
    private GachaRepository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repo = GachaRepository.getRepository(getApplication());

        binding.addUserButton.setOnClickListener(v -> createAccount());

        binding.backButton.setOnClickListener(v ->
                startActivity(MainActivity.mainActivityFactory(getApplicationContext()))
        );
    }

    private void createAccount() {
        String username = binding.usernameInputEditText.getText().toString().trim();
        String password = binding.passwordInputEditText.getText().toString().trim();

        if (username.isEmpty()) {
            toast("Username cannot be empty.");
            return;
        }

        if (password.isEmpty()) {
            toast("Password cannot be empty.");
            return;
        }

        LiveData<User> existingUserObserver = repo.getUserByUsername(username);

        existingUserObserver.observe(this, existingUser -> {
            if (existingUser != null) {
                toast("Username already taken.");
                return;
            }

            User newUser = new User(username, password);
            repo.insertUser(newUser);

            LiveData<User> storedUserObserver = repo.getUserByUsername(username);

            storedUserObserver.observe(this, storedUser -> {
                if (storedUser == null) {
                    toast("Error creating user. Try again.");
                    return;
                }

                saveLoggedInUser(storedUser.getUserID());

                Intent intent = UserActivity.userActivityFactory(
                        getApplicationContext(),
                        storedUser.getUserID()
                );

                startActivity(intent);
                finish();
            });
        });
    }

    private void saveLoggedInUser(int userID) {
        SharedPreferences sharedPreferences =
                getApplicationContext().getSharedPreferences(
                        getString(R.string.preference_file_key),
                        Context.MODE_PRIVATE
                );

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(getString(R.string.preference_userId_key), userID);
        editor.apply();
    }

    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public static Intent signupIntentFactory(Context context) {
        return new Intent(context, SignupActivity.class);
    }
}
