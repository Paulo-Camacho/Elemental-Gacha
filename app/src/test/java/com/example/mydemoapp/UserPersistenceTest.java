package com.example.mydemoapp;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class UserPersistenceTest {


    static class FakeSharedPrefs {
        private final Map<String, Integer> data = new HashMap<>();

        public void putInt(String key, int value) {
            data.put(key, value);
        }

        public int getInt(String key, int defaultValue) {
            return data.getOrDefault(key, defaultValue);
        }
    }

    @Test
    public void sharedPrefsSimulation_savesAndLoadsUserIdCorrectly() {
        FakeSharedPrefs prefs = new FakeSharedPrefs();

        int expectedId = 123;

        // Saving
        prefs.putInt("USER_ID_KEY", expectedId);

        // Quiting the app
        int actualId = prefs.getInt("USER_ID_KEY", -1);

        assertEquals(expectedId, actualId);
    }
}
