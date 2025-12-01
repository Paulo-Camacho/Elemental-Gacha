package com.example.mydemoapp.Database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.mydemoapp.Database.GachaDatabase;

import java.util.Objects;

@Entity(tableName = GachaDatabase.GACHA_TABLE)
public class GachaItem {

    @PrimaryKey(autoGenerate = true)
    private int itemId;

    private String itemName;
    private String url;
    private String rarity;


    //TODO: Implement date pulled differently
    private String datePulled;
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
    public GachaItem(String itemName, String rarity, String url) {
        this.itemName = itemName;
        this.rarity = rarity;
        this.url = url;
        // Date pulled is an empty string right now
        this.datePulled = "";
    }

    @Override
    public String toString() {
        return "GachaItem{" +
                "id=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", rarity='" + rarity + '\'' +
                ", url="+ url + '\'' +
                ", datePulled='" + datePulled + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GachaItem)) return false;
        GachaItem that = (GachaItem) o;
        return itemId == that.itemId &&
                Objects.equals(itemName, that.itemName) &&
                Objects.equals(rarity, that.rarity) &&
                Objects.equals(url,that.url) &&
                Objects.equals(datePulled, that.datePulled);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, itemName, rarity, url, datePulled);
    }


    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public String getRarity() { return rarity; }
    public void setRarity(String rarity) { this.rarity = rarity; }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDatePulled() { return datePulled; }
    public void setDatePulled(String datePulled) { this.datePulled = datePulled; }
}
