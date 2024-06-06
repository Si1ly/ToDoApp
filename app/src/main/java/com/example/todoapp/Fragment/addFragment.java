package com.example.todoapp.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todoapp.Data.Mem;
import com.example.todoapp.Data.Task;
import com.example.todoapp.Data.TaskDatabase;
import com.example.todoapp.R;


public class addFragment extends Fragment {
    EditText setName,setTask;
    Button add;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setName = view.findViewById(R.id.set_name);
        setTask = view.findViewById(R.id.set_task);
        add = view.findViewById(R.id.add_button);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = setName.getText().toString();
                String task = setTask.getText().toString();
                Task newtask = new Task(name,task,false);
                TaskDatabase.getInstance(getContext()).taskDao().insert(newtask);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add, container, false);
    }
}