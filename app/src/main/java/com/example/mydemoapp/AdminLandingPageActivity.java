package com.example.mydemoapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mydemoapp.Database.entities.User;
import com.example.mydemoapp.databinding.ActivityAdminLandingPageBinding;

public class AdminLandingPageActivity extends AppCompatActivity {
    private ActivityAdminLandingPageBinding binding;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdminLandingPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.AdminRollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(AdminEditPullRateActivity.AdminEditPullRateActivityIntentFactory(getApplicationContext()));
            }
        });

        binding = ActivityAdminLandingPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.AdminLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(UserActivity.userActivityFactory(getApplicationContext(), user.getUserID()));
            }
        });
    }

    static Intent AdminLandingPageActivityIntentFactory(Context context) {
        Intent intent = new Intent(context, AdminLandingPageActivity.class);
        return intent;
    }

}