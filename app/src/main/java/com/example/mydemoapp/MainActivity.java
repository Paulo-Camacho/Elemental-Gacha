package com.example.mydemoapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.mydemoapp.Database.GachaDatabase;
import com.example.mydemoapp.Database.GachaRepository;
import com.example.mydemoapp.Database.entities.User;
import com.example.mydemoapp.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "GACHA_TEST";
    private static final int LOGGED_OUT = -1;
    private static final String SAVED_INSTANCE_STATE_USER_ID_KEY = "com.example.mydemoapp.SAVED_INSTANCE_STATE_USER_ID_KEY";
    private static final String MAIN_ACTIVITY_USER_ID = "com.example.mydemoapp.MAIN_ACTIVITY_USER_ID";
    private int loggedInUserID = LOGGED_OUT;
    GachaRepository repo;

    private User user;

    public static Intent mainActivityFactory(Context context) {
            Intent intent = new Intent(context, MainActivity.class);
            return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.loginButtonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
            }
        });

        repo = GachaRepository.getRepository(getApplication());

        // Sanity TEST
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<User> users = repo.getUserDAO().getAllUsers();

                    for (User u : users) {
                        Log.i(TAG,
                                "User: " + u.getUsername() +
                                        " | Admin? " + u.getIsAdmin());
                    }
                } catch (Exception e) {
                    Log.wtf(TAG, "Error reading users", e);
                }
            }
        }).start();


        //hard coding including users
        User testuser1 = new User("testuser1","testuser1");
        User admin2 = new User("admin2","admin2");
        admin2.setAdmin(true);
        repo.insertUser(testuser1);
        repo.insertUser(admin2);


        loginUser(savedInstanceState);

        if(loggedInUserID != LOGGED_OUT){
            Intent intent = UserActivity.userActivityFactory(getApplicationContext(), loggedInUserID);
            startActivity(intent);
        }
    }


    private void loginUser(Bundle savedInstanceState){
        //check shared preference for logged in user
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        //checks if logged in user is in shared preferences?
        loggedInUserID = sharedPreferences.getInt(getString(R.string.preference_userId_key), LOGGED_OUT);

        //if user is logged out then try to pull it from there
        if(loggedInUserID == LOGGED_OUT && savedInstanceState != null && savedInstanceState.containsKey(SAVED_INSTANCE_STATE_USER_ID_KEY)){
            loggedInUserID = savedInstanceState.getInt(SAVED_INSTANCE_STATE_USER_ID_KEY,LOGGED_OUT);
        }
        //try to pull it from the intent
        if(loggedInUserID == LOGGED_OUT){
            loggedInUserID = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID,LOGGED_OUT);
        }
        //ditch at this point if still logged_out
        if(loggedInUserID == LOGGED_OUT){
            return;
        }

        LiveData<User> userObserver = repo.getUserByUserID(loggedInUserID);
        userObserver.observe(this,user -> {
            this.user = user;
            if(this.user != null){
                invalidateOptionsMenu();
            }

        });
    }



}