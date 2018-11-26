package com.example.test.termproject;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class FoundRegisterActivity extends AppCompatActivity implements View.OnClickListener {
    final int GALLERY_CODE = 10;
    Button register, cancel;
    ImageView img;
    Spinner spinner;
    EditText title, name, tel, explain;
    FirebaseAuth firebaseAuth;
    FirebaseStorage storage;
    FirebaseDatabase database;
    public String imagePath = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foundregister);
        if (Build.VERSION.SDK_INT >= 21) {
            // 21 버전 이상일 때
            getWindow().setStatusBarColor(Color.parseColor("#321c54"));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }
        firebaseAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();
        title = (EditText) findViewById(R.id.title);
        name = (EditText) findViewById(R.id.name);
        tel = (EditText) findViewById(R.id.phone);
        explain = (EditText) findViewById(R.id.explain);
        img = (ImageView) findViewById(R.id.imageView1);
        register = (Button) findViewById(R.id.register);
        cancel = (Button) findViewById(R.id.cancel);
        img.setOnClickListener(this);
        register.setOnClickListener(this);
        cancel.setOnClickListener(this);
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.locations, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data != null) {
            if (requestCode == GALLERY_CODE) {
                imagePath = getPath(data.getData());
                File f = new File(imagePath);
                img.setImageURI(Uri.fromFile(f));
            }
        }

    }
    public String getPath(Uri uri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(this, uri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(index);
    }
    public void upload_img(final String uri) {
        StorageReference storageRef = storage.getReference();
        Uri file = Uri.fromFile(new File(uri));
        final StorageReference riversRef = storageRef.child("Found_list/" + file.getLastPathSegment());
        final UploadTask uploadTask = riversRef.putFile(file);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        // Continue with the task to get the download URL
                        return riversRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUrl = task.getResult();
                            ImageDTO imageDTO = new ImageDTO();
                            imageDTO.title = title.getText().toString();
                            imageDTO.name = name.getText().toString();
                            imageDTO.tel = tel.getText().toString();
                            imageDTO.location = spinner.getSelectedItem().toString();
                            imageDTO.detail = explain.getText().toString();
                            imageDTO.imageUrl = downloadUrl.toString();
                            imageDTO.uid = firebaseAuth.getCurrentUser().getUid();
                            imageDTO.userId = firebaseAuth.getCurrentUser().getEmail();
                            database.getReference().child("Found_list").push().setValue(imageDTO);
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == img) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
            startActivityForResult(intent, GALLERY_CODE);
        }
        if (view == register) {
            String title_check = title.getText().toString().trim();
            String name_check = name.getText().toString().trim();
            String tel_check = tel.getText().toString().trim();
            if(imagePath==""){
                Toast.makeText(this, "사진을 등록해 주세요.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(title_check)) {
                Toast.makeText(this, "제목을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(name_check)) {
                Toast.makeText(this, "이름을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(tel_check)) {
                Toast.makeText(this, "연락처를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            upload_img(imagePath);
            Toast.makeText(getBaseContext(),"등록이 완료되었습니다.",Toast.LENGTH_SHORT).show();
            finish();
        }
        if (view == cancel) {
            finish();
        }

    }
}
