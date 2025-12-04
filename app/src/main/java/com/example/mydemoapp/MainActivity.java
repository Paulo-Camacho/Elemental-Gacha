package com.example.mydemoapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
    private User user;

    private ActivityMainBinding binding;
    private GachaRepository repo;

    public static Intent mainActivityFactory(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repo = GachaRepository.getRepository(getApplication());

        // ---- BUTTON LISTENERS ----
        binding.loginButtonMain.setOnClickListener(v ->
                startActivity(LoginActivity.loginIntentFactory(getApplicationContext()))
        );

        binding.newUserButtonMain.setOnClickListener(v ->
                startActivity(SignupActivity.signupIntentFactory(getApplicationContext()))
        );

        // DEBUGGING
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

        // ONCE USER IS MADE THEY ARE BROUGHT INTO APP
        if (loggedInUserID != LOGGED_OUT) {
            showLandingPage();
        }
    }

    /**
     * Hey, so please don't delete this
     * It shows the correct landing page for the logged in user after they close and reopen the app
     */
    private void showLandingPage() {
        try{
            LiveData<User> userObserver = repo.getUserByUserID(loggedInUserID);
            userObserver.observe(this,user -> {
                this.user = user;
                if(user.getIsPremium()){
                    Intent intent = PremiumUserLandingPageActivity.premiumUserIntentFactory(getApplicationContext(), loggedInUserID);
                    startActivity(intent);
                }
                else{
                    Intent intent = UserActivity.userActivityFactory(getApplicationContext(), loggedInUserID);
                    startActivity(intent);
                }
            });
        }catch(NullPointerException e){
            Toast.makeText(this, "User is null", Toast.LENGTH_SHORT).show();
            Intent intent = UserActivity.userActivityFactory(getApplicationContext(), loggedInUserID);
            startActivity(intent);
        }
    }


    private void loginUser(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getApplicationContext()
                .getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);

        loggedInUserID = sharedPreferences.getInt(
                getString(R.string.preference_userId_key),
                LOGGED_OUT
        );

        if (loggedInUserID == LOGGED_OUT &&
                savedInstanceState != null &&
                savedInstanceState.containsKey(SAVED_INSTANCE_STATE_USER_ID_KEY)) {

            loggedInUserID = savedInstanceState.getInt(
                    SAVED_INSTANCE_STATE_USER_ID_KEY,
                    LOGGED_OUT
            );
        }

        if (loggedInUserID == LOGGED_OUT) {
            loggedInUserID = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);
        }

        if (loggedInUserID == LOGGED_OUT) return;

        LiveData<User> userObserver = repo.getUserByUserID(loggedInUserID);
        userObserver.observe(this, user -> {
            this.user = user;
            if (this.user != null) {
                invalidateOptionsMenu();
            }
        });
    }
}