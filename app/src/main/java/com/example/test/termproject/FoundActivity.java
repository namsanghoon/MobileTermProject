package com.example.test.termproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class FoundActivity extends AppCompatActivity implements View.OnClickListener{
    Button FoundRegister;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found);
        FoundRegister = findViewById(R.id.found_register);
        FoundRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == FoundRegister){
            startActivity(new Intent(this, FoundRegisterActivity.class));
        }
    }
}
