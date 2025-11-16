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
import com.example.mydemoapp.databinding.ActivityUserLandingPageBinding;
/**
 * A landing page for users, the basic page,
 * Users can: roll, see collection, log out, move to admin
 * @author Nicholas
 * @author Nicholas Dimitrou
 * @since 3 - Nov - 2025
 */
public class UserActivity extends AppCompatActivity {

    private static final String USER_ACTIVITY_USER_ID = "com.example.mydemoapp.USER_ACTIVITY_USER_ID";
    private static final int LOGGED_OUT = -1;
    private int loggedInUserID = LOGGED_OUT;
    private ActivityUserLandingPageBinding binding;
    private User user;
    private GachaRepository repo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserLandingPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loggedInUserID = getIntent().getIntExtra(USER_ACTIVITY_USER_ID,-1);
        Toast.makeText(this,String.valueOf(loggedInUserID)+"input",Toast.LENGTH_SHORT);
        repo = GachaRepository.getRepository(getApplication());

        LiveData<User> userObserver = repo.getUserByUserID(loggedInUserID);
        userObserver.observe(this,user -> {
            this.user = user;
            Toast.makeText(this,user.toString(),Toast.LENGTH_SHORT);
            if(user != null){
                binding.userNameTextView.setText("Welcome "+user.getUsername());
                if(user.getIsAdmin()){
                  binding.adminToolsButton.setVisibility(View.VISIBLE);
                 }else{
                  binding.adminToolsButton.setVisibility(View.INVISIBLE);
                 }
            }else{
                user = new User("null","null");
                binding.userNameTextView.setText("Welcome "+user.getUsername());
            }
        });

        binding.rollMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(RollingActivity.rollingActivityFactory(getApplicationContext()));
                }
            });
        binding.logoutMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(MainActivity.mainActivityFactory(getApplicationContext()));
            }
        });
        binding.collectionMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
                startActivity(ViewCollectionActivity.viewCollectionIntentFactory(getApplicationContext()));
            }
        });
        binding.premuimMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(PremiumUserLandingPageActivity.premiumUserIntentFactory(getApplicationContext()));
            }
        });
        binding.adminToolsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(AdminLandingPageActivity.AdminLandingPageActivityIntentFactory((getApplicationContext())));
            }
        });
        updateSharedPreference();
    }

    private void logout() {
        loggedInUserID = LOGGED_OUT;
        updateSharedPreference();
        getIntent().putExtra(USER_ACTIVITY_USER_ID,LOGGED_OUT);
        startActivity((LoginActivity.loginIntentFactory(getApplicationContext())));
    }

    static Intent userActivityFactory(Context context, Integer userID){
        Intent intent = new Intent(context, UserActivity.class);
        intent.putExtra(USER_ACTIVITY_USER_ID, userID);
        return intent;
    }
    private void updateSharedPreference() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.preference_userId_key),Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPref = sharedPreferences.edit();
        sharedPref.putInt(getString(R.string.preference_userId_key),loggedInUserID);
        sharedPref.apply();
    }
}