package com.example.mydemoapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mydemoapp.Database.GachaRepository;
import com.example.mydemoapp.Database.entities.GachaItem;
import com.example.mydemoapp.databinding.ActivityAdminEditPullRateBinding;

import java.util.List;

public class AdminEditPullRateActivity extends AppCompatActivity {

    private GachaRepository repo;
    private List<GachaItem> pulls;
    private ActivityAdminEditPullRateBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdminEditPullRateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repo = GachaRepository.getRepository(getApplication());

        binding.AdminBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(AdminLandingPageActivity.AdminLandingPageActivityIntentFactory(getApplicationContext()));
            }
        });


        // Instead of using pulls, change the repo
        // repo.getID
        // use SQL methods
        binding.AdminPullRateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pulls = repo.getAllPulls();
                // ID = repo.getId
                //      repo.setRarityById(String ID)
                for (int i = 0; i < pulls.size(); i++){
                    if (pulls.get(i).getRarity().equals("rare")){
                        repo.setRarityById( i, "common");
                        //pulls.get(i).setRarity("common"); //remove this
                    } else {
                        repo.setRarityById( i, "rare");
                        //pulls.get(i).setRarity("rare");
                    }
                }
            }
        });

    }

    static Intent AdminEditPullRateActivityIntentFactory(Context context) {
        Intent intent = new Intent(context, AdminEditPullRateActivity.class);
        return intent;
    }

}