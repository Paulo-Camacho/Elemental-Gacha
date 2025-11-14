package com.example.mydemoapp.Database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.mydemoapp.Database.entities.GachaItem;
import com.example.mydemoapp.Database.entities.User;
import com.example.mydemoapp.MainActivity;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class GachaRepository {

    private final GachaItemDAO gachaDAO;
    private final UserDAO userDAO;

    private static GachaRepository repository;

    public GachaRepository(Application application) {
        GachaDatabase db = GachaDatabase.getDatabase(application);
        gachaDAO = db.gachaItemDAO();
        userDAO = db.userDAO();
    }



    public static GachaRepository getRepository(Application application) {
        if (repository != null)
            return repository;

        Future<GachaRepository> future = GachaDatabase.databaseWriteExecutor.submit(
                () -> new GachaRepository(application)
        );

        try {
            repository = future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.wtf("THIS IS HARD CODED", "Problem getting GachaRepository");
        }

        return repository;
    }

    // INSERT Gacha pull
    public void insertPull(GachaItem item) {
        GachaDatabase.databaseWriteExecutor.execute(() -> gachaDAO.insert(item));
    }

    // RETURN all pulls
    public ArrayList<GachaItem> getAllPulls() {
        Future<ArrayList<GachaItem>> future = GachaDatabase.databaseWriteExecutor.submit(() ->
                new ArrayList<>(gachaDAO.getAllPulls())
        );

        try {
            return future.get();
        } catch (Exception e) {
            Log.wtf("THIS IS HARD CODED", "Error fetching gacha pulls", e);
        }
        return null;
    }

    // USER METHODS
    public void insertUser(User user) {
        GachaDatabase.databaseWriteExecutor.execute(() -> userDAO.insert(user));
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public LiveData<User> getUserByUserID(int loggedInUserID) {
        return userDAO.getUserByUserId(loggedInUserID);
    }

    public LiveData<User> getUserByUsername(String username) {
        return userDAO.getUserByUsername(username);
    }
}
