package com.example.todoapp.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todoapp.Data.Task;
import com.example.todoapp.Data.TaskDatabase;
import com.example.todoapp.EventBus.SuaXoaEvent;
import com.example.todoapp.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class updateFragment extends DialogFragment {

    public static final String KEY_NAME = "TASK_CHOOSE";

    Task task = new Task();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_update, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText nameCs = getView().findViewById(R.id.name_cs);
        EditText taskCs = getView().findViewById(R.id.task_cs);
        Button capnhat = getView().findViewById(R.id.capnhat);


        Toast.makeText(getContext(), task.getName()+task.getTask(), Toast.LENGTH_SHORT).show();

        nameCs.setText(task.getName());
        taskCs.setText(task.getTask());
        capnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task newTask = new Task();
                TaskDatabase.getInstance(getContext()).taskDao().update(newTask);
                Toast.makeText(getContext(), "Thanh Cong", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventSuaXoa(SuaXoaEvent event){
        if(event!=null){
            task = event.getTask();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }
}