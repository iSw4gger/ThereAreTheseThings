package com.example.android.therearethesethings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jaredboynton on 6/20/17.
 */
public class CompletedToDo extends AppCompatActivity implements View.OnClickListener {

    ArrayList<String> completedArray = new ArrayList<>();
    RecyclerView completedRecyclerView;
    ToDoAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.completed_todo_items_layout);

        adapter.toDoList= getIntent().getStringArrayListExtra("name");

        completedRecyclerView = (RecyclerView) findViewById(R.id.recycler_view2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        completedRecyclerView.setAdapter(adapter);
        completedRecyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onClick(View v) {

    }
}