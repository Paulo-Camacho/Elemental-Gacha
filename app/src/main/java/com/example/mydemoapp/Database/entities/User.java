package com.example.mydemoapp.Database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.mydemoapp.Database.GachaDatabase;

import java.util.Objects;

@Entity(tableName = GachaDatabase.USER_TABLE)
public class User {



    @PrimaryKey(autoGenerate = true)
    private int userID;

    private String username;
    private String password;
    private boolean isAdmin;
    private boolean isPremium;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.isAdmin = false;
        this.isPremium = false;
    }

    // GETTERS + SETTERS
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public boolean getIsAdmin() { return isAdmin; }
    public void setAdmin(boolean admin) { isAdmin = admin; }
    public boolean getIsPremium() { return isPremium; }
    public void setPremium(boolean premium) { isPremium = premium; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return userID == user.userID &&
                isAdmin == user.isAdmin &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, username, password, isAdmin);
    }
}
