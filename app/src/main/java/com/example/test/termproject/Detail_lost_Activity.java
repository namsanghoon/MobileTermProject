package com.example.test.termproject;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import uk.co.senab.photoview.PhotoViewAttacher;

import static com.google.common.io.ByteStreams.copy;

public class Detail_lost_Activity extends AppCompatActivity implements View.OnClickListener {
    String Detail_title, Detail_name, Detail_tel, Detail_location, Detail_explain, url,userid;
    TextView title, name, tel, location, explain;
    ImageView imageView;
    Button delete;
    ImageButton message;
    FirebaseAuth firebaseAuth;
    PhotoViewAttacher mAttacher;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_detail);
        if (Build.VERSION.SDK_INT >= 21) {
            // 21 버전 이상일 때
            getWindow().setStatusBarColor(Color.parseColor("#321c54"));
        }
        firebaseAuth = FirebaseAuth.getInstance();
        message = (ImageButton) findViewById(R.id.message);
        message.setOnClickListener(this);
        Intent intent = getIntent();
        Detail_title = intent.getExtras().getString("title");
        Detail_name = intent.getExtras().getString("name");
        Detail_tel = intent.getExtras().getString("tel");
        Detail_location = intent.getExtras().getString("location");
        Detail_explain = intent.getExtras().getString("explain");
        url = intent.getExtras().getString("url");
        userid = intent.getExtras().getString("userid");

        delete = (Button)findViewById(R.id.delete);
        delete.setOnClickListener(this);


        imageView = (ImageView) findViewById(R.id.imageView1);
        imageView.setOnClickListener(this);
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

        if(firebaseAuth.getCurrentUser().getEmail().equals(userid)){
            delete.setVisibility(View.VISIBLE);
        }


    }


    @Override
    public void onClick(View view) {
        if (view == message) {
            Intent intent = new Intent(view.getContext(), MessageActivity.class);
            intent.putExtra("tel", tel.getText().toString());
            startActivity(intent);
        }
        if(view == delete) {
            if (firebaseAuth.getCurrentUser().getEmail().equals(userid)) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                Query applesQuery = ref.child("Found_list").orderByChild("title").equalTo(Detail_title);
                applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                            appleSnapshot.getRef().removeValue();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) { }
                });
            }
            finish();
        }
        if(view ==imageView){
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog);
            final ImageView iv = (ImageView) dialog.findViewById(R.id.image);
            UrlImageViewHelper.setUrlDrawable(iv, url);
            mAttacher = new PhotoViewAttacher(iv);
            dialog.show();
        }

    }

}
