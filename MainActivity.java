package com.example.android.therearethesethings;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

public class MainActivity extends AppCompatActivity implements View.OnLongClickListener {

    ArrayList<String> completedArray = new ArrayList<>();
    Boolean is_in_action_mode = false;
    TextView counter_text_view;
    RecyclerView recyclerView;
    TextView placeholderTextView;
    DatabaseHelper myDB;
    CheckBox checkBox;
    ToDoAdapter adapter;
    MenuItem completedMenuItem;
    Toolbar toolbar;
    ArrayList<String> toDoLists = new ArrayList<>();
    TextView appNameTextView;
    TextView completedPlaceHolderTextView;
    ArrayList<String> selectionArray = new ArrayList<>();
    int counter = 0;
    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layoutManager = new LinearLayoutManager(this);

        appNameTextView = (TextView) findViewById(R.id.todo_name_text_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        placeholderTextView = (TextView) findViewById(R.id.placeholder);
        myDB = new DatabaseHelper(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        checkBox = (CheckBox) findViewById(R.id.context_select_checkbox);
        completedMenuItem = (MenuItem) findViewById(R.id.completed_icon_menuitem);
        completedPlaceHolderTextView = (TextView) findViewById(R.id.completed_placeholder);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        counter_text_view = (TextView) findViewById(R.id.counter_text_view);
        counter_text_view.setVisibility(View.GONE);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), EnterToDo.class);
                startActivity(i);
            }
        });

        setAddToDo();
    }
    public void setAddToDo(){

        Cursor data = myDB.getListContents();

        if(data.getCount() == 0){
            Toast.makeText(MainActivity.this, "Empty! ", Toast.LENGTH_SHORT).show();
        }else{
            while(data.moveToNext()){
                toDoLists.add(data.getString(1));
                adapter = new ToDoAdapter(toDoLists, this);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return true;
    }

    @Override
    public boolean onLongClick(View v) {

        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.context_menu);
        adapter.notifyDataSetChanged();
        counter_text_view.setVisibility(View.VISIBLE);
        appNameTextView.setVisibility(View.INVISIBLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        is_in_action_mode = true;

        return true;
    }

    public void addToCompleted(View v, int position){

        //add to completed array
        //strike out text (can probably do this with another method)

        if(((CheckBox)v).isChecked()){
            completedArray.add(toDoLists.get(position));
        }else{
            completedArray.remove(toDoLists.get(position));
        }
    }

    public void prepareSelection(View v, int position){

        if(((CheckBox)v).isChecked()){
            selectionArray.add(toDoLists.get(position));
            counter = counter + 1;
            updateCounter(counter);
        }else{
            selectionArray.remove(toDoLists.get(position));
            counter = counter - 1;
            updateCounter(counter);
        }

    }

    public void updateCounter(int counter) {
        if (counter == 0) {
            counter_text_view.setText("0 items selected");
        } else {
            counter_text_view.setText(counter + " items selected");
        }
    }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {

            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.

            switch (item.getItemId()){

                case R.id.completed_icon_menuitem:
                    adapter.notifyDataSetChanged();
                    Intent intent = new Intent(MainActivity.this, CompletedToDo.class);
                    intent.putStringArrayListExtra("name", toDoLists);
                    startActivity(intent);

                case R.id.delete_icon_context_action:
                    ToDoAdapter toDoadapter = (ToDoAdapter) adapter;
                    toDoadapter.updateAdapter(selectionArray);
                    clearActionMode();

                case android.R.id.home:
                    clearActionMode();
                    adapter.notifyDataSetChanged();

            }

            return true;
        }

    public void clearActionMode(){

        is_in_action_mode = false;
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.activity_menu);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        counter_text_view.setVisibility(View.GONE);
        counter_text_view.setText(" 0 items selected");
        counter = 0;
        selectionArray.clear();

    }

    public void onBackPressed(){
        if(is_in_action_mode){
            clearActionMode();
            adapter.notifyDataSetChanged();
        }
        else{
            super.onBackPressed();
        }
    }

}


