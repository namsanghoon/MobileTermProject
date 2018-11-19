package com.example.test.termproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class FoundActivity extends AppCompatActivity implements View.OnClickListener{
    Button FoundRegister;
    public static  final String TAG ="FoundActivity";
    private  DatabaseHelper databaseHelper;
    SQLiteDatabase db;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found);
        FoundRegister = findViewById(R.id.found_register);
        FoundRegister.setOnClickListener(this);

        ListView list = (ListView)findViewById(R.id.listview);
        boolean isOpen = openDatabase();
        /*if(isOpen){
            Cursor cursor = execRawQuery();
            //startManagingCursor(cursor);
            String[] column = new String[]{"name", "tel","photo"};
            int[] to = new int[]{R.id.nametext,R.id.agetext,R.id.imgview};
            SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this,R.layout.item_layout,cursor,column,to);
            list.setAdapter(simpleCursorAdapter);
        }*/
    }
    private boolean openDatabase(){
        databaseHelper = new DatabaseHelper(this);
        db = databaseHelper.getWritableDatabase();
        return true;
    }

    private Cursor execRawQuery(){
        String sql = "select _id, name, tel, photo from lost_list where tel > ?";
        String[] args = {"20"};
        Cursor cursor = db.rawQuery(sql,args);

        return cursor;
    }



    @Override
    public void onClick(View view) {
        if(view == FoundRegister){
            startActivity(new Intent(this, FoundRegisterActivity.class));
        }
    }
}
