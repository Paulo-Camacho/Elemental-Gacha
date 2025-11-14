package com.example.mydemoapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
<<<<<<< HEAD
import android.view.View;
=======
import android.util.Log;
>>>>>>> database-paulo

import androidx.appcompat.app.AppCompatActivity;

import com.example.mydemoapp.Database.GachaDatabase;
import com.example.mydemoapp.Database.GachaRepository;
import com.example.mydemoapp.Database.entities.User;

import java.util.List;

import com.example.mydemoapp.databinding.ActivityMainBinding;

<<<<<<< HEAD
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.loginButtonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(UserActivity.userActivityFactory(getApplicationContext()));
            }
        });
    }
    static Intent mainActivityFactory(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }
}
=======
    private static final String TAG = "GACHA_TEST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
>>>>>>> database-paulo
