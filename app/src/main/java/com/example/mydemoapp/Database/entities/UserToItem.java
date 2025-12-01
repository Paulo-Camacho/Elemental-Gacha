package com.example.mydemoapp.Database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.mydemoapp.Database.GachaDatabase;

import java.util.Objects;

@Entity(tableName = GachaDatabase.USER_ITEM_TABLE)
public class UserToItem {

    @PrimaryKey(autoGenerate = true)
    private int userToItemID;

    private int userId;

    public int getUserToItemID() {
        return userToItemID;
    }

    public void setUserToItemID(int userToItemID) {
        this.userToItemID = userToItemID;
    }

    private int itemId;

    @Override
    public String toString() {
        return "UserToItem{" +
                "id=" + userToItemID +
                ", userId=" + userId +
                ", itemId=" + itemId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserToItem that = (UserToItem) o;
        return userToItemID == that.userToItemID && userId == that.userId && itemId == that.itemId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userToItemID, userId, itemId);
    }

    public UserToItem(int userId, int itemId) {
        this.userId = userId;
        this.itemId = itemId;
    }

    public int getId() {
        return userToItemID;
    }

    public void setId(int id) {
        this.userToItemID = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
}
