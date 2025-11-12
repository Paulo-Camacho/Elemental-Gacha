package com.example.mydemoapp;

import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mydemoapp.databinding.ActivityViewCollectionBinding;

/**
 * Collection View
 * Users can: see collection, go back
 * @author Nat Chelonis
 * @since 3 - Nov - 2025
 */
public class ViewCollectionActivity extends AppCompatActivity {

    private ActivityViewCollectionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityViewCollectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //TODO: make this send user back to correct landing page
        binding.collectionBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = PremiumUserLandingPageActivity.premiumUserIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });


    }

    static Intent viewCollectionIntentFactory(Context context){
        return new Intent(context, ViewCollectionActivity.class);
    }
}