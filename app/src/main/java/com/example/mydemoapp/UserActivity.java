package com.example.mydemoapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mydemoapp.databinding.ActivityUserLandingPageBinding;

public class UserActivity extends AppCompatActivity {

    private ActivityUserLandingPageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserLandingPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}