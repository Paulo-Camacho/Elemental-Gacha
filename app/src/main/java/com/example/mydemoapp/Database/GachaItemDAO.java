package com.example.mydemoapp.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mydemoapp.Database.entities.GachaItem;

import java.util.List;

@Dao
public interface GachaItemDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(GachaItem item);

    @Query("SELECT * FROM " + GachaDatabase.GACHA_TABLE + " ORDER BY datePulled DESC")
    List<GachaItem> getAllPulls();
}
