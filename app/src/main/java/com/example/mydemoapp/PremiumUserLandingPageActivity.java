package com.example.mydemoapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mydemoapp.databinding.ActivityPremiumUserLandingPageBinding;


/**
 * A landing page for Premium users, like the basic page, but sparkly
 * Users can: roll, see collection, log out
 * @author Nat Chelonis
 * @since 3 - Nov - 2025
 */
public class PremiumUserLandingPageActivity extends AppCompatActivity {

    private ActivityPremiumUserLandingPageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPremiumUserLandingPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.premiumUserLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.mainActivityFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        binding.premiumUserViewCollectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ViewCollectionActivity.viewCollectionIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

    }

    static Intent premiumUserIntentFactory(Context context){
        return new Intent(context, PremiumUserLandingPageActivity.class);
    }
}