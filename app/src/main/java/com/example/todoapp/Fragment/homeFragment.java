package com.example.todoapp.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todoapp.Adapter.TaskAdapter;
import com.example.todoapp.Data.Mem;
import com.example.todoapp.Data.TaskDAO;
import com.example.todoapp.Data.TaskDatabase;
import com.example.todoapp.EventBus.SuaXoaEvent;
import com.example.todoapp.R;
import com.example.todoapp.Data.Task;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class homeFragment extends Fragment {

    public static final String KEY_NAME = "TASK_CHOOSE";
    RecyclerView rcv_Task;
    List<Task> taskList;
    TaskAdapter taskAdapter;

    Task taskChoose = new Task();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        rcv_Task = view.findViewById(R.id.rcv_listTask);
        taskAdapter = new TaskAdapter();

        taskList = TaskDatabase.getInstance(getContext()).taskDao().getAll();
        taskAdapter.setData(taskList);
        rcv_Task.setHasFixedSize(true);
        rcv_Task.setLayoutManager(new LinearLayoutManager(getContext()));
        rcv_Task.setAdapter(taskAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getTitle().equals("Sửa")){
            suaTask();
        }else if(item.getTitle().equals("Xóa")){
            xoaTask();

        }
        return super.onContextItemSelected(item);
    }

    private void xoaTask() {
        TaskDatabase.getInstance(getContext()).taskDao().delete(taskChoose);
        Toast.makeText(getContext(), taskChoose.getName() + taskChoose.getTask(), Toast.LENGTH_SHORT).show();
    }

    private void suaTask() {
        updateFragment update = new updateFragment();
        update.show(getParentFragmentManager(),"fragmentUpdate");
        FragmentManager fm = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.commit();

        EventBus.getDefault().post(new SuaXoaEvent(taskChoose));
    }



    @Override
    public void onResume() {
        super.onResume();
        taskList = TaskDatabase.getInstance(getContext()).taskDao().getAll();
        taskAdapter.setData(taskList);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void eventSuaXoa(SuaXoaEvent event){
        if(event!=null){
            taskChoose = event.getTask();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}