package com.example.todoapp.Adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.Data.Mem;
import com.example.todoapp.EventBus.SuaXoaEvent;
import com.example.todoapp.Interface.ItemClickListener;
import com.example.todoapp.R;
import com.example.todoapp.Data.Task;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolderTask> {

    List<Task> listTask;
    Context context;
    Task task = new Task();

    public void setData(List<Task> list){
        this.listTask = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolderTask onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment,parent,false);
        return new ViewHolderTask(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolderTask holder, int position) {
        holder.task.setText(listTask.get(position).getTask());
        holder.name.setText(listTask.get(position).getName());
        holder.done.setChecked(listTask.get(position).isDone());

        holder.setItemClick(new ItemClickListener() {
            @Override
            public void ItemClick(View view, int pos, boolean isLongClick) {
                task  = listTask.get(pos);
                if (!isLongClick) {
                    EventBus.getDefault().postSticky(new SuaXoaEvent(task));
                }
            };
        });
    }

    @Override
    public int getItemCount() {
        if(listTask!=null){
            return listTask.size();
        }
        return 0;
    }

    public class ViewHolderTask extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener, View.OnCreateContextMenuListener {
        CheckBox done;
        TextView name,task;
        ItemClickListener itemClick;

        public void setItemClick(ItemClickListener itemClick) {
            this.itemClick = itemClick;
        }

        public ViewHolderTask(@NonNull View itemView) {
            super(itemView);
            done = itemView.findViewById(R.id.check_task);
            name = itemView.findViewById(R.id.name_task);
            task = itemView. findViewById(R.id.misstion_task);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }


        @Override
        public void onClick(View view) {
            itemClick.ItemClick(view,getAdapterPosition(),false);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.add(0,0,getAdapterPosition(),"Sửa");
            contextMenu.add(0,1,getAdapterPosition(),"Xóa");
        }

        @Override
        public boolean onLongClick(View view) {
            itemClick.ItemClick(view,getAdapterPosition(),true);
            return false;
        }
    }
}

