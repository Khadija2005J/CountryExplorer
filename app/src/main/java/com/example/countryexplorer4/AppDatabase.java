package com.example.countryexplorer4;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {FavoriteCountry.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract CountryDao countryDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "country_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries() // Pour la simplicité de l'exemple, on autorise sur le main thread
                    .build();
        }
        return instance;
    }
}
