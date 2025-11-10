package com.example.mydemoapp;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mydemoapp.databinding.ActivityRollingBinding;

public class RollingActivity extends AppCompatActivity {
    ActivityRollingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRollingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}