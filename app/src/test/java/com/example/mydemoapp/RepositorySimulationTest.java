package com.example.mydemoapp;

import static org.junit.Assert.*;

import com.example.mydemoapp.Database.entities.User;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class RepositorySimulationTest {

    static class FakeUserDao {
        private final Map<Integer, User> storage = new HashMap<>();
        private int nextId = 1;

        public void insert(User user) {
            user.setUserID(nextId++);
            storage.put(user.getUserID(), user);
        }

        public User getUserById(int id) {
            return storage.get(id);
        }
    }

    @Test
    public void fakeRepository_insertsAndFetchesUserCorrectly() {
        FakeUserDao fakeDao = new FakeUserDao();

        User u = new User("admin", "s3cr3t");
        fakeDao.insert(u);

        User loaded = fakeDao.getUserById(u.getUserID());

        assertNotNull(loaded);
        assertEquals("admin", loaded.getUsername());
        assertEquals("s3cr3t", loaded.getPassword());
    }
}
