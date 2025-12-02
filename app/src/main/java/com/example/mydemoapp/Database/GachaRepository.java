package com.example.mydemoapp.Database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.mydemoapp.Database.entities.GachaItem;
import com.example.mydemoapp.Database.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class GachaRepository {

    private final GachaItemDAO gachaDAO;
    private final UserDAO userDAO;
    private final UserItemDAO userItemDAO;

    private static GachaRepository repository;

    private static final String TAG = "GACHA_REPO";

    private GachaRepository(Application application) {
        GachaDatabase db = GachaDatabase.getDatabase(application);
        this.gachaDAO = db.gachaItemDAO();
        this.userDAO = db.userDAO();
        this.userItemDAO = db.userItemDAO();
    }

    //   REPOSITORY FACTORY (SINGLETON)
    public static GachaRepository getRepository(Application application) {

        if (repository != null) {
            return repository;
        }

        Future<GachaRepository> future =
                GachaDatabase.databaseWriteExecutor.submit(
                        new Callable<GachaRepository>() {
                            @Override
                            public GachaRepository call() {
                                return new GachaRepository(application);
                            }
                        }
                );

        try {
            repository = future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.wtf(TAG, "Error creating GachaRepository", e);
        }

        return repository;
    }

    //   GACHA ITEM METHODS

    // Results?... bro who plays these
    public void insertPull(GachaItem item) {
        GachaDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                gachaDAO.insert(item);
            }
        });
    }

    // GET all pulls, THIS IS NOT LIVE YET
    public ArrayList<GachaItem> getAllPulls() {

        Future<ArrayList<GachaItem>> future =
                GachaDatabase.databaseWriteExecutor.submit(
                        new Callable<ArrayList<GachaItem>>() {
                            @Override
                            public ArrayList<GachaItem> call() {
                                return new ArrayList<GachaItem>(gachaDAO.getAllPulls());
                            }
                        }
                );

        try {
            return future.get();
        } catch (Exception e) {
            Log.wtf(TAG, "Error fetching gacha pulls", e);
        }

        return null;
    }

    //   USER METHODS
    public void insertUser(User user) {
        GachaDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                userDAO.insert(user);
            }
        });
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public LiveData<User> getUserByUserID(int userID) {
        return userDAO.getUserByUserId(userID);
    }

    public LiveData<User> getUserByUsername(String username) {
        return userDAO.getUserByUsername(username);
    }

    public LiveData<List<GachaItem>> getAllPullsLiveData() {
        return userItemDAO.getAllPullsLiveData();
    }
}
