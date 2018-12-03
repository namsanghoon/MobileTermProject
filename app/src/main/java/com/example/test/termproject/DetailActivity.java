package com.example.test.termproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static com.google.common.io.ByteStreams.copy;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener{
    String Detail_title,Detail_name,Detail_tel,Detail_location,Detail_explain,url;
    TextView title,name,tel,location,explain;
    ImageView imageView;
    ImageButton message;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (Build.VERSION.SDK_INT >= 21) {
            // 21 버전 이상일 때
            getWindow().setStatusBarColor(Color.parseColor("#321c54"));
        }
        message = (ImageButton)findViewById(R.id.message);
        message.setOnClickListener(this);
        Intent intent=getIntent();
        Detail_title = intent.getExtras().getString("title");
        Detail_name = intent.getExtras().getString("name");
        Detail_tel = intent.getExtras().getString("tel");
        Detail_location = intent.getExtras().getString("location");
        Detail_explain = intent.getExtras().getString("explain");
        url = intent.getExtras().getString("url");

        imageView = (ImageView)findViewById(R.id.imageView1);
        title = (TextView) findViewById(R.id.title);
        name = (TextView) findViewById(R.id.name);
        tel = (TextView) findViewById(R.id.phone);
        location = (TextView) findViewById(R.id.location);
        explain = (TextView) findViewById(R.id.explain);
        title.setText(Detail_title);
        name.setText(Detail_name);
        tel.setText(Detail_tel);
        location.setText(Detail_location.toString());
        explain.setText(Detail_explain.toString());
        UrlImageViewHelper.setUrlDrawable(imageView, url);

    }


    @Override
    public void onClick(View view) {
        if(view==message){
            Intent intent = new Intent(view.getContext(),MessageActivity.class);
            intent.putExtra("tel",tel.getText().toString());
            startActivity(intent);
        }

    }
}
