package com.example.test.termproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class LostActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton LostRegister;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost);
        LostRegister =(ImageButton)findViewById(R.id.LostRegister);
        LostRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == LostRegister){
            Intent intent = new Intent(LostActivity.this,LostRegisterActivity.class);
            startActivity(intent);
        }
    }
}
