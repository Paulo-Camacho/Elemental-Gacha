package com.example.mydemoapp;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.example.mydemoapp.Database.GachaDatabase;
import com.example.mydemoapp.Database.UserDAO;
import com.example.mydemoapp.Database.entities.User;

import java.io.IOException;
import java.util.List;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private UserDAO userDao;
    private GachaDatabase database;

    @Before
    public void createDb(){
        Context context = ApplicationProvider.getApplicationContext();
        database = Room.inMemoryDatabaseBuilder(context, GachaDatabase.class).build();
        userDao = database.userDAO();
    }

    @After
    public void closeDb() throws IOException{
        database.close();
    }

    /**
     * making sure users are being added to the database
     * Nat :)
     */
    @Test
    public void writeUserAndReadInList(){
        String username = "testuser1";
        String password = "testuser1";
        User user = new User(username,password);

        userDao.insert(user);

        List<User> users = userDao.getAllUsers();
        assertNotNull(users.get(0));
        assertEquals(username,users.get(0).getUsername());

    }


    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.mydemoapp", appContext.getPackageName());
    }
}