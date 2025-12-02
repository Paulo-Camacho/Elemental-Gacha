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

                User admin = new User("admin2", "admin2");
                admin.setAdmin(true);
                dao.insert(admin);

                User test = new User("testuser1", "testuser1");
                test.setAdmin(false);
                dao.insert(test);

                User premium = new User("premium3", "premium3");
                premium.setPremium(true);
                dao.insert(premium);
            });
        }
    };
}


