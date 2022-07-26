package com.bluesoftx.noone.Room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = { MessagesDB.class, UserProfile.class}, version = 1, exportSchema = false)
public abstract class RoomIdDatabase extends RoomDatabase {


    public abstract RoomDao roomDao();
    private static RoomIdDatabase instance;
    private static final String DB_NAME = "chatHistory.db";


    public static synchronized RoomIdDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    RoomIdDatabase.class,
                    DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
