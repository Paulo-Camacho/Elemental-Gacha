package com.example.mydemoapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.mydemoapp.Database.GachaRepository;
import com.example.mydemoapp.Database.entities.User;
import com.example.mydemoapp.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "GACHA_TEST";
    private static final int LOGGED_OUT = -1;

    private static final String SAVED_INSTANCE_STATE_USER_ID_KEY =
            "com.example.mydemoapp.SAVED_INSTANCE_STATE_USER_ID_KEY";

    private static final String MAIN_ACTIVITY_USER_ID =
            "com.example.mydemoapp.MAIN_ACTIVITY_USER_ID";

    private int loggedInUserID = LOGGED_OUT;

    private GachaRepository repo;
    private User user;

    public static Intent mainActivityFactory(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repo = GachaRepository.getRepository(getApplication());

        binding.loginButtonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
            }
        });

        // DEBUG REMOVE THIS LATER!
        new Thread(() -> {
            try {
                List<User> users = repo.getUserDAO().getAllUsers();
                for (User u : users) {
                    Log.i(TAG, "User: " + u.getUsername() + " | Admin? " + u.getIsAdmin());
                }
            } catch (Exception e) {
                Log.wtf(TAG, "Error reading users", e);
            }
        }).start();

        loginUser(savedInstanceState);

        if (loggedInUserID != LOGGED_OUT) {
            Intent intent = UserActivity.userActivityFactory(
                    getApplicationContext(),
                    loggedInUserID
            );
            startActivity(intent);
            finish();
        }
    }

    private void loginUser(Bundle savedInstanceState) {

        SharedPreferences sharedPreferences = getApplicationContext()
                .getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);

        loggedInUserID =
                sharedPreferences.getInt(getString(R.string.preference_userId_key), LOGGED_OUT);

        // Try saved instance state
        if (loggedInUserID == LOGGED_OUT &&
                savedInstanceState != null &&
                savedInstanceState.containsKey(SAVED_INSTANCE_STATE_USER_ID_KEY)) {

            loggedInUserID =
                    savedInstanceState.getInt(SAVED_INSTANCE_STATE_USER_ID_KEY, LOGGED_OUT);
        }

        // Try intent
        if (loggedInUserID == LOGGED_OUT) {
            loggedInUserID = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);
        }

        if (loggedInUserID == LOGGED_OUT) {
            return; // still nobody logged in
        }

        LiveData<User> userObserver = repo.getUserByUserID(loggedInUserID);
        userObserver.observe(this, user -> {
            this.user = user;
            if (user != null) {
                invalidateOptionsMenu();
            }
        });
    }
}
