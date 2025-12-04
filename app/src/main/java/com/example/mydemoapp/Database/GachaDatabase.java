package com.example.mydemoapp.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

//import com.example.mydemoapp.Database.converters.LocalDateConverter;
import com.example.mydemoapp.Database.entities.GachaItem;
import com.example.mydemoapp.Database.entities.User;
import com.example.mydemoapp.Database.entities.UserToItem;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {GachaItem.class, User.class, UserToItem.class}, version = 9, exportSchema = false)
//@TypeConverters({LocalDateConverter.class})
public abstract class GachaDatabase extends RoomDatabase {

    public static final String GACHA_TABLE = "gacha_table";
    public static final String USER_TABLE = "user_table";
    public static final String USER_ITEM_TABLE = "user_item_table";

    private static final String DATABASE_NAME = "Gacha_Database";

    private static volatile GachaDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public abstract GachaItemDAO gachaItemDAO();
    public abstract UserDAO userDAO();
    public abstract UserItemDAO userItemDAO();

    public static GachaDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (GachaDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    GachaDatabase.class,
                                    DATABASE_NAME
                            )
                            .fallbackToDestructiveMigration(true)
                            .addCallback(addDefaultUsers)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // PRELOAD admin + testuser1
    private static final Callback addDefaultUsers = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                UserDAO dao = INSTANCE.userDAO();
                dao.deleteAll();
                GachaItemDAO gacha = INSTANCE.gachaItemDAO();
                gacha.deleteAll();
                UserItemDAO connect = INSTANCE.userItemDAO();
                connect.deleteAll();

                User admin = new User("admin2", "admin2");
                admin.setAdmin(true);
                dao.insert(admin);

                User test = new User("testuser1", "testuser1");
                test.setAdmin(false);
                dao.insert(test);

                User premium = new User("premium3", "premium3");
                premium.setPremium(true);
                dao.insert(premium);

                GachaItem one = new GachaItem("1do", "common","https://cdn2.thecatapi.com/images/1do.png");
                GachaItem two = new GachaItem("5qc", "common","https://cdn2.thecatapi.com/images/5qc.jpg");
                GachaItem three = new GachaItem("8r4", "common","https://cdn2.thecatapi.com/images/8r4.jpg");
                GachaItem four = new GachaItem("9tu", "common","https://cdn2.thecatapi.com/images/9tu.jpg");
                GachaItem five = new GachaItem("amo", "common","https://cdn2.thecatapi.com/images/amo.jpg");
                GachaItem six = new GachaItem("dfq", "rare","https://cdn2.thecatapi.com/images/dfq.jpg");
                GachaItem seven = new GachaItem("MTUxMjcxNw", "rare","https://cdn2.thecatapi.com/images/MTUxMjcxNw.jpg");
                GachaItem eight = new GachaItem("MTk3NTA0OA", "rare","https://cdn2.thecatapi.com/images/MTk3NTA0OA.jpg");
                GachaItem nine = new GachaItem("MTk1NjcyNg", "rare","https://cdn2.thecatapi.com/images/MTk1NjcyNg.jpg");
                GachaItem ten = new GachaItem("ajWdNxBwn", "rare","https://cdn2.thecatapi.com/images/ajWdNxBwn.jpg");
                gacha.insert(one);
                gacha.insert(two);
                gacha.insert(three);
                gacha.insert(four);
                gacha.insert(five);
                gacha.insert(six);
                gacha.insert(seven);
                gacha.insert(eight);
                gacha.insert(nine);
                gacha.insert(ten);

                UserToItem first = new UserToItem(3,1);
                UserToItem second = new UserToItem(3,2);
                connect.insert(first);
                connect.insert(second);
            });
        }
    };
}


