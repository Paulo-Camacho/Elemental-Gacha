package com.example.mydemoapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
/**
 * the page to roll on
 * Users can: roll to add to thier collection
 * @author Nicholas Dimitriou
 * @since 3 - Nov - 2025
 */
import com.example.mydemoapp.databinding.ActivityRollingBinding;

public class RollingActivity extends AppCompatActivity {
    ActivityRollingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRollingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.backButtonRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(UserActivity.userActivityFactory(getApplicationContext()));
            }
        });
    }
    static Intent rollingActivityFactory(Context context){
        Intent intent = new Intent(context, RollingActivity.class);
        return intent;
    }
}