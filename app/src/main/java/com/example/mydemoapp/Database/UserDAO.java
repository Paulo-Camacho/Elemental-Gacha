package com.example.mydemoapp.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mydemoapp.Database.entities.User;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User... users);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM " + GachaDatabase.USER_TABLE + " ORDER BY username")
    List<User> getAllUsers();

    @Query("DELETE FROM " + GachaDatabase.USER_TABLE)
    void deleteAll();

    @Query("SELECT * FROM " + GachaDatabase.USER_TABLE + " WHERE username = :username LIMIT 1")
    User getUserByUsername(String username);
    @Query("SELECT * FROM " + GachaDatabase.USER_TABLE+ " WHERE id == :userId")
    User getUserByUserId(int userId);
}
