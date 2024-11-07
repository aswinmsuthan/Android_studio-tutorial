package com.mycompany.todotask.TaskAdapter;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mycompany.todotask.Model.TaskModel;
import com.mycompany.todotask.R;

import java.util.ArrayList;


public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private ArrayList<TaskModel> taskList;

    // Interface to handle deletion of a task from the list
    private onTaskDeleteListener deleteListener;
    private onTaskUpdateListener updateListener;

    // Interfaces for delete and update actions
    public interface onTaskDeleteListener {
        void onDeleteTask(int position);
    }
    public interface onTaskUpdateListener{
        void onUpdateTask(int position);
    }

    public TaskAdapter(ArrayList<TaskModel> taskList, onTaskDeleteListener deleteListener, onTaskUpdateListener updateListener) {
        this.taskList = taskList;
        this.deleteListener = deleteListener;
        this.updateListener = updateListener;
    }

    // Creates and returns a new ViewHolder for displaying a single contact item
    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tasks, parent, false);
        return new TaskViewHolder(view, deleteListener, updateListener);
    }

    // Binds data from a task object to the UI elements in the ViewHolder
    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        TaskModel task = taskList.get(position);
        holder.taskNames.setText(task.getTask());
        holder.taskNames.setChecked(toBoolean(task.getStatus()));
    }

    private boolean toBoolean(int n) {
        return n != 0;
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }


    // Static inner class ViewHolder holding UI references for each contact item
    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        CheckBox taskNames;
        ImageButton delTaskBtn;

        public TaskViewHolder(View view, onTaskDeleteListener deleteListener, onTaskUpdateListener updateListener) {
            super(view);

            // Reference to UI elements
            taskNames = view.findViewById(R.id.todoTasks);
            delTaskBtn = view.findViewById(R.id.deleteBtn);

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    updateListener.onUpdateTask(getAdapterPosition());
                    return false;
                }
            });

            // Sets a click listener for the delete button
            delTaskBtn.setOnClickListener(new View.OnClickListener() {

                // Calls the onDeleteTask method in deleteListener, passing the contact's adapter position
                @Override
                public void onClick(View v) {
                    deleteListener.onDeleteTask(TaskViewHolder.this.getAdapterPosition());
                }
            });

        }
    }
}
