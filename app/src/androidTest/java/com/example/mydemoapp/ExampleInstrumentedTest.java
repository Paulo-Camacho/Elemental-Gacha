package com.example.mydemoapp;

import android.content.Context;
import android.content.Intent;

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
import com.example.mydemoapp.Database.GachaItemDAO;
import com.example.mydemoapp.Database.UserDAO;
import com.example.mydemoapp.Database.UserItemDAO;
import com.example.mydemoapp.Database.entities.GachaItem;
import com.example.mydemoapp.Database.entities.User;
import com.example.mydemoapp.Database.entities.UserToItem;

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
    private GachaItemDAO itemDao;
    private UserItemDAO connectionDao;
    private GachaDatabase database;

    @Before
    public void createDb(){
        Context context = ApplicationProvider.getApplicationContext();
        database = Room.inMemoryDatabaseBuilder(context, GachaDatabase.class).build();
        userDao = database.userDAO();
        itemDao = database.gachaItemDAO();
        connectionDao = database.userItemDAO();
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
        //create and insert a user
        String username = "testuser1";
        String password = "testuser1";
        User user = new User(username,password);

        userDao.insert(user);

        //read in the user
        List<User> users = userDao.getAllUsers();
        assertNotNull(users.get(0));
        assertEquals(username,users.get(0).getUsername());
    }

    /**
     * Tests deletes the user
     * Nat :)
     */
    @Test
    public void deleteUserTest(){
        //create and insert a user
        String username = "testuser1";
        String password = "testuser1";
        User user = new User(username,password);

        userDao.insert(user);

        //delete the user
        //pretty sure this isn't working because of a live data issue
        userDao.delete(user);
        List<User> noUsers = userDao.getAllUsers();
        assertEquals(true,noUsers.isEmpty());
    }

    /**
     * testing the premium user landing page intent
     * Nat :)
     */
    @Test
    public void testPremiumIntent(){
        Context context = ApplicationProvider.getApplicationContext();

        Intent intent = PremiumUserLandingPageActivity.premiumUserIntentFactory(context,1);

        assertNotNull(intent);
        assertNotNull(intent.getComponent());
        assertEquals(PremiumUserLandingPageActivity.class.getName(), intent.getComponent().getClassName());
    }

    /**
     * testing the collections page intent
     * Nat :)
     */
    @Test
    public void testCollectionsIntent(){
        Context context = ApplicationProvider.getApplicationContext();

        Intent intent = ViewCollectionActivity.viewCollectionIntentFactory(context,1);

        assertNotNull(intent);
        assertNotNull(intent.getComponent());
        assertEquals(ViewCollectionActivity.class.getName(), intent.getComponent().getClassName());
    }
    /**
     * Nicholas
     * testing Rolling intent
     */
    @Test
    public void testRollingIntent(){
        Context context = ApplicationProvider.getApplicationContext();

        Intent intent = RollingActivity.rollingActivityFactory(context,1);

        assertNotNull(intent);
        assertNotNull(intent.getComponent());
        assertEquals(RollingActivity.class.getName(), intent.getComponent().getClassName());
    }
    /**
     * Nicholas
     * testing User intent
     */
    @Test
    public void testUserActivityIntent(){
        Context context = ApplicationProvider.getApplicationContext();

        Intent intent = UserActivity.userActivityFactory(context,1);

        assertNotNull(intent);
        assertNotNull(intent.getComponent());
        assertEquals(UserActivity.class.getName(), intent.getComponent().getClassName());
    }
    /**
     * Nicholas
     * testing Main intent
     */
    @Test
    public void testMainActivityIntent(){
        Context context = ApplicationProvider.getApplicationContext();

        Intent intent = MainActivity.mainActivityFactory(context);

        assertNotNull(intent);
        assertNotNull(intent.getComponent());
        assertEquals(MainActivity.class.getName(), intent.getComponent().getClassName());
    }
    /**
     * Nicholas
     * Verify insert works for items
     */
    @Test
    public void writeItemAndReadInList(){
        String name = "hold";
        String url = "over";
        GachaItem item = new GachaItem(name,"common", url);

        itemDao.insert(item);

        List<GachaItem> items = itemDao.getAllPulls();
        assertNotNull(items.get(0));
        assertEquals(name,items.get(0).getItemName());
    }
    /**
     * Nicholas
     * Verify insert works for usertoitems database
     */
    @Test
    public void writeConnectionAndReadInList(){
        UserToItem item = new UserToItem(1,1);

        connectionDao.insert(item);

        List<UserToItem> items = connectionDao.getAllPulls();
        assertNotNull(items.get(0));
        assertEquals(1,items.get(0).getUserId());
    }
    /**
     * Nicholas
     * Verify delete works for databses
     */
    @Test
    public void deleteDatabases(){
        UserToItem connect = new UserToItem(1,1);

        connectionDao.insert(connect);

        connectionDao.delete(connect);
        List<UserToItem> items = connectionDao.getAllPulls();
        assertEquals(true,items.isEmpty());
        String name = "hold";
        String url = "over";
        GachaItem item = new GachaItem(name,"common", url);

        itemDao.insert(item);

        itemDao.delete(item);
        List<GachaItem> pulls = itemDao.getAllPulls();
        assertEquals(true,pulls.isEmpty());
    }
    /**
     * Nicholas
     * Verify that i can update values in the database
     */
    @Test
    public void updateDatabases(){
        UserToItem connect = new UserToItem(1,1);
        String name = "hold";
        String url = "over";
        GachaItem item = new GachaItem(name,"common", url);
        User user = new User("Username", "password");
        assertEquals(1,connect.getUserId());
        connect.setUserId(2);
        assertEquals(2,connect.getUserId());
        assertEquals("password",user.getPassword());
        user.setPassword("change");
        assertEquals("change",user.getPassword());
        assertEquals("common",item.getRarity());
        item.setRarity("rare");
        assertEquals("rare", item.getRarity());
    }
    /**
     * Andrew
     * Verify that the AdminLandingPageActivity intent factory
     * creates an intent that targets AdminLandingPageActivity.
     */
    @Test
    public void adminLandingPageIntentFactoryCreatesCorrectIntent() {
        Context context = ApplicationProvider.getApplicationContext();

        Intent intent = AdminLandingPageActivity.AdminLandingPageActivityIntentFactory(context);

        assertNotNull(intent);
        assertNotNull(intent.getComponent());
        assertEquals(AdminLandingPageActivity.class.getName(), intent.getComponent().getClassName());
    }

    /**
     * Andrew
     * Verify that the AdminEditPullRateActivity intent factory
     * creates an intent that targets AdminEditPullRateActivity.
     */
    @Test
    public void adminEditPullRateIntentFactoryCreatesCorrectIntent() {
        Context context = ApplicationProvider.getApplicationContext();

        Intent intent = AdminEditPullRateActivity.AdminEditPullRateActivityIntentFactory(context);

        assertNotNull(intent);
        assertNotNull(intent.getComponent());
        assertEquals(AdminEditPullRateActivity.class.getName(), intent.getComponent().getClassName());
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.mydemoapp", appContext.getPackageName());
    }
}