package com.example.test.termproject;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class LostRegisterActivity extends AppCompatActivity  {
    final static String TAG ="LostRegisterActivity";
    final int GALLERY_CODE = 10;
    DatabaseHelper databaseHelper;
    Button register;
    ImageView img;
    Spinner spinner;
    EditText name, tel, explain;
    Uri imageUri;
    String encodedImageString;
    Bitmap bitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_register);

        databaseHelper = new DatabaseHelper(this);

        name = (EditText) findViewById(R.id.name);
        tel = (EditText) findViewById(R.id.phone);
        explain = (EditText) findViewById(R.id.explain);
        img = (ImageView) findViewById(R.id.imageView1);
        register = (Button) findViewById(R.id.register);

        img.setOnClickListener(listener);
        register.setOnClickListener(listener);

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.locations, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }

    View.OnClickListener listener = new View.OnClickListener() {
        SQLiteDatabase sDB;
        ContentValues data;

        public void onClick(View v) {
            switch (v.getId()){
                case R.id.imageView1:
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                    startActivityForResult(intent, GALLERY_CODE);
                    break;

                case R.id.register:
                    try {
                        sDB = databaseHelper.getWritableDatabase();
                        data = new ContentValues();

                        data.put("name", name.getText().toString());
                        data.put("tel", tel.getText().toString());
                        data.put("location", spinner.getSelectedItem().toString());
                        data.put("detail", explain.getText().toString());
                        data.put("photo",encodedImageString.toString());

                        sDB.insert("lost_list", null, data);
                        databaseHelper.close();
                        Toast.makeText(getBaseContext(),"등록완료",Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }



        }
    };

    //Bitmap bitmap = ((BitmapDrawable)img.getDrawable()).getBitmap();
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GALLERY_CODE) {
            //imgbit = (Bitmap)data.getExtras().get("data");
            //bitmap = ((BitmapDrawable)img.getDrawable()).getBitmap();
            imageUri = data.getData();
            getContentResolver().notifyChange(imageUri, null);
            ContentResolver cr = getContentResolver();
            try {
                bitmap = MediaStore.Images.Media
                        .getBitmap(cr, imageUri);
                img.setImageBitmap(bitmap);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] b = baos.toByteArray();
                encodedImageString = Base64.encodeToString(b, Base64.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }


}
