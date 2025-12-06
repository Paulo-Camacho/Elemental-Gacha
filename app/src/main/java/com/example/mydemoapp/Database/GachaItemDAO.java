package com.example.mydemoapp.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mydemoapp.Database.entities.GachaItem;
import com.example.mydemoapp.Database.entities.User;

import java.util.List;

@Dao
public interface GachaItemDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(GachaItem item);
    @Delete
    void delete(GachaItem item);
    @Query("DELETE FROM " + GachaDatabase.GACHA_TABLE)
    void deleteAll();
    @Query("SELECT * FROM " + GachaDatabase.GACHA_TABLE + " ORDER BY itemId DESC")
    List<GachaItem> getAllPulls();
    @Query("SELECT * FROM " + GachaDatabase.GACHA_TABLE+ " WHERE itemId == :itemId")
    LiveData<GachaItem> getItemsByItemId(int itemId);

    // getRarityById
    // setItemById
    /**
     * UPDATE your_table_name
     * SET parameter_column = @new_value
     * WHERE id = @item_id;
     */
    @Query("UPDATE " + GachaDatabase.GACHA_TABLE + " set rarity = :rarity WHERE itemId == :itemId")
    void setRarityById(int itemID, String rarity);

    // setRarityById
}
