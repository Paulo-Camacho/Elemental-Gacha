package com.example.mydemoapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mydemoapp.databinding.ActivityAdminEditPullRateBinding;

public class AdminEditPullRateActivity extends AppCompatActivity {

    private ActivityAdminEditPullRateBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdminEditPullRateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.AdminBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(AdminLandingPageActivity.AdminLandingPageActivityIntentFactory(getApplicationContext()));
            }
        });

        // TODO: Make new activity or add textView in XML to allow data manipulation in this activity.

    }

    static Intent AdminEditPullRateActivityIntentFactory(Context context) {
        Intent intent = new Intent(context, AdminEditPullRateActivity.class);
        return intent;
    }

}