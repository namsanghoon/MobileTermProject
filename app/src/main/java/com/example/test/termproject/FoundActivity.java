package com.example.test.termproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FoundActivity extends AppCompatActivity implements View.OnClickListener{
    Button FoundRegister;
    public RecyclerView recyclerView;
    public List<ImageDTO> imageDTOS = new ArrayList<>();
    public FirebaseDatabase database;
    String date;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found);
        FoundRegister = findViewById(R.id.found_register);
        FoundRegister.setOnClickListener(this);
        database = FirebaseDatabase.getInstance();
        date = new SimpleDateFormat("yyyy년 MM월 dd일").format(new Date());
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final BoardRecycleViewAdapter boardRecycleViewAdapter = new BoardRecycleViewAdapter();
        recyclerView.setAdapter(boardRecycleViewAdapter);

        database.getReference().child("Found_list").addValueEventListener(new ValueEventListener() {
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
            }*/

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
                textView3 = (TextView) view.findViewById(R.id.data);
            }
        }


    }



    @Override
    public void onClick(View view) {
        if(view == FoundRegister){
            startActivity(new Intent(this, FoundRegisterActivity.class));
        }
    }
}
