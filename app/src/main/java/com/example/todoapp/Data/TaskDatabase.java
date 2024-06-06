package com.example.todoapp.Data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Task.class},version = 1)
public abstract class TaskDatabase extends RoomDatabase {

    private static final String NAME = "task.db";
    private static TaskDatabase instance;
    public static synchronized TaskDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),TaskDatabase.class,NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract TaskDAO taskDao();
}
