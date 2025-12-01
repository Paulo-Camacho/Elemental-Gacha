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

public class UserActivity extends AppCompatActivity {

    private static final String USER_ACTIVITY_USER_ID =
            "com.example.mydemoapp.USER_ACTIVITY_USER_ID";

    private static final int LOGGED_OUT = -1;

    private ActivityUserLandingPageBinding binding;
    private GachaRepository repo;

    private int loggedInUserID = LOGGED_OUT;
    private User user;

    private boolean isAdmin = false;
    private boolean isPremium = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserLandingPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repo = GachaRepository.getRepository(getApplication());

        LiveData<User> userObserver = repo.getUserByUserID(loggedInUserID);
        userObserver.observe(this,user -> {
            this.user = user;
            Toast.makeText(this,user.toString(),Toast.LENGTH_SHORT);
            if(user != null){
                binding.userNameTextView.setText("Welcome "+user.getUsername());
                if(user.getIsAdmin()){
                  binding.adminToolsButton.setVisibility(View.VISIBLE);
                  admin = true;
                  if(user.getIsPremium()){
                      toastMaker("you cannot be premium as a admin");
                      user.setPremium(false);

                  }
                 }else{
                    if(user.getIsPremium()){
                        premium = true;
                    }
                  binding.adminToolsButton.setVisibility(View.INVISIBLE);
                 }
            }else{
                user = new User("nullUser","null");
                binding.userNameTextView.setText("Welcome "+user.getUsername());
            }
        });
        // Pull user from the intent
        loggedInUserID = getIntent().getIntExtra(USER_ACTIVITY_USER_ID, LOGGED_OUT);

        if (loggedInUserID == LOGGED_OUT) {
            logout(); // invalid state, force logout
            return;
        }

        loadUserFromDatabase();

        // BUTTON LISTENERS

        binding.rollMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(RollingActivity.rollingActivityFactory(getApplicationContext(), user.getUserID()));
                }
            });
        binding.logoutMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(ViewCollectionActivity.viewCollectionIntentFactory(
                        getApplicationContext(),
                        loggedInUserID
                ));
            }
        });

        binding.logoutMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
                startActivity(ViewCollectionActivity.viewCollectionIntentFactory(getApplicationContext(), user.getUserID()));
            }
        });

        binding.premuimMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isAdmin) {
                    toastMaker("Admins cannot be premium.");
                    return;
                }

                if (!isPremium) {
                    user.setPremium(true);
                    isPremium = true;
                    toastMaker("You are now a premium user.");
                }
                startActivity(PremiumUserLandingPageActivity.premiumUserIntentFactory(getApplicationContext(), user.getUserID()));
            }
        });

        binding.adminToolsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(AdminLandingPageActivity
                        .AdminLandingPageActivityIntentFactory(getApplicationContext()));
            }
        });
    }

    private void loadUserFromDatabase() {
        LiveData<User> userObserver = repo.getUserByUserID(loggedInUserID);

        userObserver.observe(this, newUser -> {
            if (newUser == null) {
                toastMaker("User not found. Logging out.");
                logout();
                return;
            }

            this.user = newUser;

            binding.userNameTextView.setText("Welcome " + user.getUsername());

            isAdmin = user.getIsAdmin();
            isPremium = user.getIsPremium();

            if (isAdmin) {
                binding.adminToolsButton.setVisibility(View.VISIBLE);
            } else {
                binding.adminToolsButton.setVisibility(View.INVISIBLE);
            }

            updateSharedPreference();
        });
    }

    private void logout() {
        // Clear SharedPreferences
        SharedPreferences sharedPreferences = getApplicationContext()
                .getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(getString(R.string.preference_userId_key), LOGGED_OUT);
        editor.apply();

        // Go to MainActivity
        Intent intent = MainActivity.mainActivityFactory(getApplicationContext());
        startActivity(intent);
        // prevents back button from returning here
        finish();
    }

    static Intent userActivityFactory(Context context, int userID) {
        Intent intent = new Intent(context, UserActivity.class);
        intent.putExtra(USER_ACTIVITY_USER_ID, userID);
        return intent;
    }

    private void updateSharedPreference() {
        SharedPreferences sharedPreferences = getApplicationContext()
                .getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(getString(R.string.preference_userId_key), loggedInUserID);
        editor.apply();
    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
