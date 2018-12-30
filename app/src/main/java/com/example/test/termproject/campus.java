package com.example.test.termproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import uk.co.senab.photoview.PhotoViewAttacher;


public class campus extends AppCompatActivity implements View.OnClickListener {
    PhotoViewAttacher mAttacher,mAttacher_2;
    ImageView map,detail;
    Spinner spinner;
    Button move;
    Button list;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campus);
        map = (ImageView)findViewById(R.id.map);
        detail = (ImageView)findViewById(R.id.detail);
        move = (Button)findViewById(R.id.move);
        mAttacher = new PhotoViewAttacher(map);
        mAttacher_2 = new PhotoViewAttacher(detail);
        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.locations, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        move.setOnClickListener(this);
        list = (Button) findViewById(R.id.list);
        list.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == move){
            Intent intent = new Intent(this,location_lost_Activity.class);
            intent.putExtra("location",spinner.getSelectedItem().toString());
            startActivity(intent);
        }
        if(view==list){
            Intent intent = new Intent(this,LostActivity.class);
            startActivity(intent);
        }
    }
}
