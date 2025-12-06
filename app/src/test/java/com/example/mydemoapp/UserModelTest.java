package com.example.mydemoapp;

import com.example.mydemoapp.Database.entities.User;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserModelTest {

    @Test
    public void userModel_setsAndGetsValuesCorrectly() {
        User user = new User("paulo", "admin");

        user.setUserID(10);
        user.setAdmin(true);
        user.setPremium(true);

        assertEquals(10, user.getUserID());
        assertEquals("paulo", user.getUsername());
        assertEquals("admin", user.getPassword());
        assertTrue(user.getIsAdmin());
        assertTrue(user.getIsPremium());
    }
}
