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
import com.example.mydemoapp.databinding.ActivityPremiumUserLandingPageBinding;


/**
 * A landing page for Premium users, like the basic page, but sparkly
 * Users can: roll, see collection, log out
 * @author Nat Chelonis
 * @since 3 - Nov - 2025
 */
public class PremiumUserLandingPageActivity extends AppCompatActivity {

    private static final int LOGGED_OUT = -1;
    private static final String PREMIUM_USER_ACTIVITY_USER_ID = "com.example.mydemoapp.PREMIUM_USER_ACTIVITY_USER_ID";
    private ActivityPremiumUserLandingPageBinding binding;
    private int loggedInUserID = LOGGED_OUT;
    private User user;
    private GachaRepository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPremiumUserLandingPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repo = GachaRepository.getRepository(getApplication());

        //get the logged in user from the intent
        loggedInUserID = getIntent().getIntExtra(PREMIUM_USER_ACTIVITY_USER_ID,LOGGED_OUT);
        LiveData<User> userObserver = repo.getUserByUserID(loggedInUserID);
        userObserver.observe(this, user1 -> {
            this.user = user1;
            if(user != null){
                //set the text to be the username
                String placeholder = "Welcome Premium User:\n" + user.getUsername();
                binding.premiumUserTitleTextView.setText(placeholder);
            }else{
                String placeholder = "User is null";
                binding.premiumUserTitleTextView.setText(placeholder);
            }
        });


        binding.premiumUserLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
                Intent intent = MainActivity.mainActivityFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        binding.premiumUserViewCollectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ViewCollectionActivity.viewCollectionIntentFactory(getApplicationContext(), loggedInUserID);
                startActivity(intent);
            }
        });

        binding.noMorePremiumSadEyesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    user.setPremium(false);
                }catch(NullPointerException e){
                    toastMaker("User is null");
                }
                Intent intent = UserActivity.userActivityFactory(getApplicationContext(),loggedInUserID);
                startActivity(intent);
            }
        });

    }

    static Intent premiumUserIntentFactory(Context context, int userID){
        Intent intent = new Intent(context, PremiumUserLandingPageActivity.class);
        intent.putExtra(PREMIUM_USER_ACTIVITY_USER_ID,userID);
        return intent;
    }

    private void updateSharedPreference() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.preference_userId_key),Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPref = sharedPreferences.edit();
        sharedPref.putInt(getString(R.string.preference_userId_key),loggedInUserID);
        sharedPref.apply();
    }

    private void logout() {
        loggedInUserID = LOGGED_OUT;
        updateSharedPreference();
        getIntent().putExtra(PREMIUM_USER_ACTIVITY_USER_ID,LOGGED_OUT);
        startActivity((LoginActivity.loginIntentFactory(getApplicationContext())));
    }

    private void toastMaker(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}