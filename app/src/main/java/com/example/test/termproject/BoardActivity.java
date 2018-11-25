/*
package com.example.test.termproject;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.net.UrlQuerySanitizer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class BoardActivity extends AppCompatActivity {
    public RecyclerView recyclerView;
    public List<ImageDTO> imageDTOS = new ArrayList<>();
    public FirebaseDatabase database;
    String date;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        {
            setContentView(R.layout.activity_board);
            database = FirebaseDatabase.getInstance();
            date = new SimpleDateFormat("yyyy년 MM월 DD일").format(new Date());
            recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            final BoardRecycleViewAdapter boardRecycleViewAdapter = new BoardRecycleViewAdapter();
            recyclerView.setAdapter(boardRecycleViewAdapter);

            database.getReference().child("Lost_list").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    imageDTOS.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ImageDTO imageDTO = snapshot.getValue(ImageDTO.class);
                        imageDTOS.add(imageDTO);
                    }
                    boardRecycleViewAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }
    }

    class BoardRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);

            return new CustomViewHolder(view);
        }


        @Override
        public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
            ((CustomViewHolder) holder).textView.setText(imageDTOS.get(position).title);
            ((CustomViewHolder) holder).textView2.setText(imageDTOS.get(position).name);
            ((CustomViewHolder) holder).textView3.setText(date);
            Glide.with(holder.itemView.getContext()).load(imageDTOS.get(position).imageUrl).into(((CustomViewHolder) holder).imageView);
            // url = imageDTOS.get(position).imageUrl;
           */
/* try {
                URL urls = new URL(url);
                HttpURLConnection urlConnection = (HttpURLConnection) urls.openConnection();
                urlConnection.connect();
                InputStream iStream = urlConnection.getInputStream();
                final Bitmap bitmap = BitmapFactory.decodeStream(iStream);
                ((CustomViewHolder) holder).imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        ((CustomViewHolder) holder).imageView.setImageBitmap(bitmap);

                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }*//*


        }

        @Override
        public int getItemCount() {
            return imageDTOS.size();
        }

        private class CustomViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView textView;
            TextView textView2;
            TextView textView3;

            public CustomViewHolder(View view) {
                super(view);
                imageView = (ImageView) view.findViewById(R.id.imgview);
                textView = (TextView) view.findViewById(R.id.title);
                textView2 = (TextView) view.findViewById(R.id.name);
                textView3 = (TextView) view.findViewById(R.id.name);
            }
        }


    }
}*/
