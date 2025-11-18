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

import com.example.mydemoapp.Database.entities.User;
import com.example.mydemoapp.databinding.ActivityViewCollectionBinding;

/**
 * Collection View
 * Users can: see collection, go back
 * @author Nat Chelonis
 * @since 3 - Nov - 2025
 */
public class ViewCollectionActivity extends AppCompatActivity {

    private static final int LOGGED_OUT = -1;
    private static final String COLLECTION_ACTIVITY_USER_ID = "com.example.mydemoapp.COLLECTION_ACTIVITY_USER_ID";
    private ActivityViewCollectionBinding binding;
    private int loggedInUserID = LOGGED_OUT;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityViewCollectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //TODO: make this send user back to correct landing page
        binding.collectionBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = PremiumUserLandingPageActivity.premiumUserIntentFactory(getApplicationContext(),loggedInUserID);
                startActivity(intent);
            }
        });


    }

    static Intent viewCollectionIntentFactory(Context context, int userID){
        Intent intent = new Intent(context, ViewCollectionActivity.class);
        intent.putExtra(COLLECTION_ACTIVITY_USER_ID,userID);
        return intent;
    }
}