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
import androidx.lifecycle.LiveData;

import com.example.mydemoapp.Database.GachaRepository;
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
    private GachaRepository repo;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityViewCollectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repo = GachaRepository.getRepository(getApplication());

        //get the logged in user from the intent
        loggedInUserID = getIntent().getIntExtra(COLLECTION_ACTIVITY_USER_ID,LOGGED_OUT);
        LiveData<User> userObserver = repo.getUserByUserID(loggedInUserID);
        userObserver.observe(this, user1 -> {
            this.user = user1;
            if(user != null){
                //set the text to be the username
                String placeholder = user.getUsername() + "'s Collection";
                binding.collectionTitleTextView.setText(placeholder);
            }else{
                String placeholder = "This user is nonexistent and therefore has no collection.";
                binding.collectionTitleTextView.setText(placeholder);
            }
        });

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