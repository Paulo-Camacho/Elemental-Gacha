package com.example.mydemoapp.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mydemoapp.Database.entities.UserToItem;

import java.util.List;

@Dao
public interface UserItemDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserToItem connection);

    @Query("SELECT * FROM " + GachaDatabase.USER_ITEM_TABLE + " ORDER BY userToItemID")
    List<UserToItem> getAllPulls();
    @Query("SELECT itemId FROM " + GachaDatabase.USER_ITEM_TABLE + " WHERE userID = :userId")
    LiveData<List<Integer>> getItemIdByUsers(int userId);
}
