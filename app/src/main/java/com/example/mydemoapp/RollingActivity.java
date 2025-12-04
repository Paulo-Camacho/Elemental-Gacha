package com.example.mydemoapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

/**
 * the page to roll on
 * Users can: roll to add to thier collection
 * @author Nicholas Dimitriou
 * @since 3 - Nov - 2025
 */
import com.example.mydemoapp.Database.GachaRepository;
import com.example.mydemoapp.Database.entities.User;
import com.example.mydemoapp.databinding.ActivityRollingBinding;

public class RollingActivity extends AppCompatActivity {
    ActivityRollingBinding binding;
    private static final String USER_ACTIVITY_USER_ID = "com.example.mydemoapp.USER_ACTIVITY_USER_ID";
    private int loggedInUserID;
    private User user;
    private GachaRepository repo;
    private boolean isPremuim = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRollingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loggedInUserID = getIntent().getIntExtra(USER_ACTIVITY_USER_ID,-1);
        Toast.makeText(this,String.valueOf(loggedInUserID)+"input",Toast.LENGTH_SHORT);
        repo = GachaRepository.getRepository(getApplication());

        LiveData<User> userObserver = repo.getUserByUserID(loggedInUserID);
        userObserver.observe(this,user -> {
                    this.user = user;
                    if(user.getIsPremium()){
                        isPremuim = true;
                        Toast.makeText(this,"premium", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(this, user.toString(), Toast.LENGTH_SHORT).show();
                });
        binding.backButtonRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isPremuim){
                    startActivity(PremiumUserLandingPageActivity.premiumUserIntentFactory(getApplicationContext(), user.getUserID()));
                }else{
                    startActivity(UserActivity.userActivityFactory(getApplicationContext(),user.getUserID()));
                }


            }
        });
    }
    static Intent rollingActivityFactory(Context context, Integer userID){
        Intent intent = new Intent(context, RollingActivity.class);
        intent.putExtra(USER_ACTIVITY_USER_ID, userID);
        return intent;
    }
}