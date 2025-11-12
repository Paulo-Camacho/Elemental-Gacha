package com.example.mydemoapp;

import android.os.Bundle;

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
    }
}