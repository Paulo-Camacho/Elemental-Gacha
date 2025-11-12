package com.example.mydemoapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mydemoapp.databinding.ActivityUserLandingPageBinding;
/**
 * A landing page for users, the basic page,
 * Users can: roll, see collection, log out, move to admin
 * @author Nicholas
 * @author Nicholas Dimitrou
 * @since 3 - Nov - 2025
 */
public class UserActivity extends AppCompatActivity {

    private ActivityUserLandingPageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserLandingPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
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



    }
    static Intent userActivityFactory(Context context){
        Intent intent = new Intent(context, UserActivity.class);
        return intent;
    }
}