package com.example.mydemoapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mydemoapp.Database.GachaRepository;
import com.example.mydemoapp.Database.entities.User;
import com.example.mydemoapp.databinding.ActivityViewCollectionBinding;
import com.example.mydemoapp.viewHolders.GachaAdapter;
import com.example.mydemoapp.viewHolders.GachaItemViewModel;

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
    private GachaItemViewModel gachaViewModel;

    private User user;
    private boolean isPremium = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityViewCollectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        gachaViewModel = new ViewModelProvider(this).get(GachaItemViewModel.class);

        RecyclerView recyclerView = binding.gachaItemDisplayRecyclerView;
        final GachaAdapter adapter = new GachaAdapter(new GachaAdapter.GachaItemDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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
                isPremium = user.getIsPremium();
            }else{
                String placeholder = "This user is nonexistent and therefore has no collection.";
                binding.collectionTitleTextView.setText(placeholder);
            }
        });

        gachaViewModel.getAllItemsByUserID(loggedInUserID).observe(this,gachaItems -> {
            adapter.submitList(gachaItems);
        });

        binding.collectionBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPremium){
                    Intent intent = PremiumUserLandingPageActivity.premiumUserIntentFactory(getApplicationContext(),loggedInUserID);
                    startActivity(intent);
                }
                else{
                    Intent intent = UserActivity.userActivityFactory(getApplicationContext(),loggedInUserID);
                    startActivity(intent);
                }

            }
        });



    }

    static Intent viewCollectionIntentFactory(Context context, int userID){
        Intent intent = new Intent(context, ViewCollectionActivity.class);
        intent.putExtra(COLLECTION_ACTIVITY_USER_ID,userID);
        return intent;
    }
}