package com.example.test.termproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.google.firebase.auth.FirebaseAuth;


public class AfterLoginActivity extends AppCompatActivity implements View.OnClickListener {
     Button Logout;
     Button Lost;
     Button Found;
     FirebaseAuth firebaseAuth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afterlogin);
        firebaseAuth = FirebaseAuth.getInstance();
        Logout = (Button) findViewById(R.id.logout);
        Lost = (Button) findViewById(R.id.lost);
        Found = (Button) findViewById(R.id.found);
        Logout.setOnClickListener(this);
        Lost.setOnClickListener(this);
        Found.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        if(view == Logout) {
            firebaseAuth.signOut();
            finish();
            Intent intent = new Intent(AfterLoginActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        if(view == Lost){
            finish();
            Intent intent = new Intent(AfterLoginActivity.this, LostActivity.class);
            startActivity(intent);
        }
        if(view == Found){
            finish();
            Intent intent = new Intent(AfterLoginActivity.this, FoundActivity.class);
            startActivity(intent);
        }
    }
}


