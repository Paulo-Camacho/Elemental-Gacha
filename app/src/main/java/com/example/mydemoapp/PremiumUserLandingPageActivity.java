package com.example.mydemoapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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


        binding.premiumUserRollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = RollingActivity.rollingActivityFactory(getApplicationContext() , loggedInUserID);
                startActivity(intent);
            }
        });

        binding.premiumUserLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutDialog();
            }
        });

        binding.premiumUserViewCollectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ViewCollectionActivity.viewCollectionIntentFactory(getApplicationContext(), loggedInUserID);
                startActivity(intent);
            }
        });

        //allow them to go back to the normal landing page
        binding.noMorePremiumSadEyesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEndPremiumDialog();
            }
        });

        //why would they ever cancel their premium though
        binding.noMorePremiumSadEyesButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                toastMaker("Wait no. Come back.");
                return false;
            }
        });
    }

    static Intent premiumUserIntentFactory(Context context, int userID){
        Intent intent = new Intent(context, PremiumUserLandingPageActivity.class);
        intent.putExtra(PREMIUM_USER_ACTIVITY_USER_ID,userID);
        return intent;
    }

    /**
     * fixes shared preferences so the user is actually logged out
     */
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

    private void toastMaker(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Shows the dialog box for logging out
     * If user clicks positive button, then the logout() function is called
     */
    private void showLogoutDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(PremiumUserLandingPageActivity.this);
        final AlertDialog alertDialog = alertBuilder.create();
        alertBuilder.setMessage("Would you like to logout?");
        alertBuilder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logout();
            }
        });

        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        alertBuilder.create().show();
    }


    private void showEndPremiumDialog(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(PremiumUserLandingPageActivity.this);
        final AlertDialog alertDialog = alertBuilder.create();
        alertBuilder.setMessage("Are you sure you want to end premium?");
        alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                endPremium();
            }
        });

        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        alertBuilder.create().show();
    }

    private void endPremium() {
        try{
            user.setPremium(false);
        }catch(NullPointerException e){
            toastMaker("User is null");
        }
        Intent intent = UserActivity.userActivityFactory(getApplicationContext(),loggedInUserID);
        startActivity(intent);
    }

}