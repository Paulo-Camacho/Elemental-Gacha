package com.example.mydemoapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mydemoapp.Database.GachaDatabase;
import com.example.mydemoapp.Database.GachaRepository;
import com.example.mydemoapp.Database.entities.User;
import com.example.mydemoapp.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "GACHA_TEST";
    private static final int LOGGED_OUT = -1;
    public static Intent mainActivityFactory(Context context) {
        try{
            Intent intent = new Intent(context, MainActivity.class);
            return intent;
        }catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.loginButtonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
            }
        });

        GachaRepository repo = GachaRepository.getRepository(getApplication());

        // Sanity TEST
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<User> users = repo.getUserDAO().getAllUsers();

                    for (User u : users) {
                        Log.i(TAG,
                                "User: " + u.getUsername() +
                                        " | Admin? " + u.isAdmin());
                    }
                } catch (Exception e) {
                    Log.wtf(TAG, "Error reading users", e);
                }
            }
        }).start();
    }

}