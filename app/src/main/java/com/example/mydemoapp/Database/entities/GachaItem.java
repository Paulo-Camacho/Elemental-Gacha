package com.example.mydemoapp.Database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.mydemoapp.Database.GachaDatabase;

import java.util.Objects;

@Entity(tableName = GachaDatabase.GACHA_TABLE)
public class GachaItem {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String itemName;
    private String rarity;
    //TODO: Implement date pulled differently
    private String datePulled;

    public GachaItem(String itemName, String rarity) {
        this.itemName = itemName;
        this.rarity = rarity;
        // Date pulled is an empty string right now
        this.datePulled = "";
    }

    @Override
    public String toString() {
        return "GachaItem{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", rarity='" + rarity + '\'' +
                ", datePulled='" + datePulled + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GachaItem)) return false;
        GachaItem that = (GachaItem) o;
        return id == that.id &&
                Objects.equals(itemName, that.itemName) &&
                Objects.equals(rarity, that.rarity) &&
                Objects.equals(datePulled, that.datePulled);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, itemName, rarity, datePulled);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public String getRarity() { return rarity; }
    public void setRarity(String rarity) { this.rarity = rarity; }

    public String getDatePulled() { return datePulled; }
    public void setDatePulled(String datePulled) { this.datePulled = datePulled; }
}
