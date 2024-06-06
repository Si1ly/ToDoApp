package com.example.todoapp.EventBus;

import com.example.todoapp.Data.Task;

public class SuaXoaEvent {
    Task task;

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public SuaXoaEvent(Task task) {
        this.task = task;
    }
}
