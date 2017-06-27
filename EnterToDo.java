package com.example.android.therearethesethings;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class EnterToDo extends AppCompatActivity {

    Button submit;
    EditText enterToDoEditText;
    FloatingActionButton fab;
    DatabaseHelper myDB;
    TextView placeholderTextView;
    RadioGroup priorityGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_to_do);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myDB = new DatabaseHelper(this);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.INVISIBLE);
        placeholderTextView = (TextView) findViewById(R.id.placeholder);
        submit = (Button) findViewById(R.id.submit_to_do_button);
        enterToDoEditText = (EditText) findViewById(R.id.enter_edit_text);
        priorityGroup = (RadioGroup) findViewById(R.id.radio_group);

    }

    public void onClick(View v){
        String toDoData = enterToDoEditText.getText().toString();
        if(toDoData.length() != 0){
            AddData(toDoData);
            enterToDoEditText.setText("");
        }else{
            Toast.makeText(EnterToDo.this, "You must enter something. ", Toast.LENGTH_SHORT).show();

        }

        Intent i = new Intent(this, MainActivity.class);

        startActivity(i);    }

    public void AddData(String toDoData){
        boolean insertData = myDB.addData(toDoData);

        if(insertData == true){
            Toast.makeText(EnterToDo.this, "Success! ", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(EnterToDo.this, "Fail!", Toast.LENGTH_SHORT).show();

        }
    }

}
