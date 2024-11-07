package com.mycompany.todotask;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mycompany.todotask.Model.TaskModel;
import com.mycompany.todotask.TaskAdapter.TaskAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private ArrayList<TaskModel> taskList;
    private TaskAdapter adapter;
    private int selectedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Set up Toolbar as ActionBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        recyclerView = findViewById(R.id.recyclerviewTask);
        fab = findViewById(R.id.fab);

        taskList = new ArrayList<>();
        adapter = new TaskAdapter(taskList, this::deleteTask, this::updateTaskDialog);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.showTaskDialog();
            }
        });

    }

    private void showTaskDialog() {
        View dialogview = LayoutInflater.from(this).inflate(R.layout.dialog_add_task, null);
        EditText inputTask = dialogview.findViewById(R.id.inputTask);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Task");
        builder.setView(dialogview);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String task = inputTask.getText().toString().trim();

                if (!task.isEmpty()) {
                    taskList.add(new TaskModel(task, 0));
                    adapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Task added...", Toast.LENGTH_LONG).show();


                }
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
    // Dialog to update an existing task
    private void updateTaskDialog(int position){
        View dialogview = LayoutInflater.from(this).inflate(R.layout.dialog_add_task, null);
        EditText inputTask = dialogview.findViewById(R.id.inputTask);
        // Populate the EditText with the existing task text
        inputTask.setText(taskList.get(position).getTask());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update Task");
        builder.setView(dialogview);
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String updatedTask = inputTask.getText().toString().trim();
                if(!updatedTask.isEmpty()){
                    taskList.get(position).setTask(updatedTask);
                    adapter.notifyItemChanged(position);

                }
            }

        });
        builder.setNegativeButton("Cancel", null);
        builder.show();

    }
    private void deleteTask(int position){
        taskList.remove(position);
        adapter.notifyItemRemoved(position);
        Toast.makeText(MainActivity.this, "Task is deleted", Toast.LENGTH_LONG).show();

    }
}