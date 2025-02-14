package com.example.todoapp.Data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface TaskDAO {
    @Query("SELECT * FROM task")
    List<Task> getAll();

    @Insert()
    void insert(Task task);

    @Delete
    void delete(Task task);

    @Update
    void update(Task task);

}
