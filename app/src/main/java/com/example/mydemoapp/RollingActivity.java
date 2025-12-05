package com.example.mydemoapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

/**
 * the page to roll on
 * Users can: roll to add to their collection
 * @author Nicholas Dimitriou
 * @since 3 - Nov - 2025
 */
import com.example.mydemoapp.Database.GachaRepository;
import com.example.mydemoapp.Database.UserItemDAO;
import com.example.mydemoapp.Database.entities.GachaItem;
import com.example.mydemoapp.Database.entities.User;
import com.example.mydemoapp.Database.entities.UserToItem;
import com.example.mydemoapp.databinding.ActivityRollingBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RollingActivity extends AppCompatActivity {
    ActivityRollingBinding binding;
    private static final String USER_ACTIVITY_USER_ID = "com.example.mydemoapp.USER_ACTIVITY_USER_ID";
    private int loggedInUserID;
    private User user;
    private GachaRepository repo;
    private boolean isPremuim = false;
    private boolean isAdmin = false;
    private List<GachaItem> pulls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRollingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loggedInUserID = getIntent().getIntExtra(USER_ACTIVITY_USER_ID,-1);
        repo = GachaRepository.getRepository(getApplication());

        LiveData<User> userObserver = repo.getUserByUserID(loggedInUserID);
        userObserver.observe(this,user -> {
                    this.user = user;
                    if(user.getIsPremium()){
                        isPremuim = true;
                        Toast.makeText(this,"premium", Toast.LENGTH_SHORT).show();
                    }
                    if(user.getIsAdmin()){
                        isAdmin = true;
                    }
                    Toast.makeText(this, user.toString(), Toast.LENGTH_SHORT).show();
                });
        binding.backButtonRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isPremuim && !isAdmin){
                    startActivity(PremiumUserLandingPageActivity.premiumUserIntentFactory(getApplicationContext(), user.getUserID()));
                }else{
                    startActivity(UserActivity.userActivityFactory(getApplicationContext(),user.getUserID()));
                }


            }
        });
        binding.rollButtonRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageView = findViewById(R.id.outputRollImageView);
                Picasso.get().load("https://en.wikipedia.org/wiki/Throbber#/media/File:Ajax_loader_metal_512.gif").into(imageView);
                pulls = repo.getAllPulls();
                int random = (int) (Math.random()*pulls.size());
                if(pulls.get(random).getRarity().equals("rare")){
                    random = (int) (Math.random()*pulls.size());
                }
                String url = pulls.get(random).getUrl();
                GachaItem added = pulls.get(random);
                Toast.makeText(RollingActivity.this,String.valueOf(added.getItemId())+"+"+String.valueOf(user.getUserID()),Toast.LENGTH_SHORT).show();
                UserToItem connection = new UserToItem(user.getUserID(),added.getItemId());
                repo.insertConnection(connection);
                Picasso.get().load(url).into(imageView);
            }
        });
    }
    static Intent rollingActivityFactory(Context context, Integer userID){
        Intent intent = new Intent(context, RollingActivity.class);
        intent.putExtra(USER_ACTIVITY_USER_ID, userID);
        return intent;
    }
}