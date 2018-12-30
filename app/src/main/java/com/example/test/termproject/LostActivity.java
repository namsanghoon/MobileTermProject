package com.example.test.termproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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

import uk.co.senab.photoview.PhotoViewAttacher;

public class LostActivity extends AppCompatActivity implements View.OnClickListener {
    Button  campus, list, move, LostRegister;
    public RecyclerView recyclerView;
    public List<ImageDTO> imageDTOS = new ArrayList<>();
    public FirebaseDatabase database;
    String date;
    PhotoViewAttacher mAttacher,mAttacher_2;
    ImageView map,detail;
    Spinner spinner;
    LinearLayout listLayout, campusLayout;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost);
        campus = (Button) findViewById(R.id.campus);
        LostRegister = (Button) findViewById(R.id.LostRegister);
        LostRegister.setOnClickListener(this);
        campus.setOnClickListener(this);
        database = FirebaseDatabase.getInstance();
        date = new SimpleDateFormat("yyyy년 MM월 dd일").format(new Date());
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final BoardRecycleViewAdapter boardRecycleViewAdapter = new BoardRecycleViewAdapter();
        recyclerView.setAdapter(boardRecycleViewAdapter);

        listLayout = (LinearLayout) findViewById(R.id.listLayout);
        campusLayout = (LinearLayout) findViewById(R.id.campusLayout);

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

    class BoardRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);

            return new BoardRecycleViewAdapter.CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
            ((CustomViewHolder) holder).textView.setText(imageDTOS.get(position).title);
            ((CustomViewHolder) holder).textView2.setText(imageDTOS.get(position).name);
            ((CustomViewHolder) holder).textView3.setText(date);
            ((CustomViewHolder) holder).textView4.setText(imageDTOS.get(position).tel);
            ((CustomViewHolder) holder).textView5.setText(imageDTOS.get(position).location);
            ((CustomViewHolder) holder).textView6.setText(imageDTOS.get(position).detail);
            ((CustomViewHolder) holder).textView7.setText(imageDTOS.get(position).imageUrl);
            ((CustomViewHolder) holder).textView8.setText(imageDTOS.get(position).userId);
            Glide.with(holder.itemView.getContext()).load(imageDTOS.get(position).imageUrl).into((((CustomViewHolder) holder).imageView));
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
            TextView textView4;
            TextView textView5;
            TextView textView6;
            TextView textView7;
            TextView textView8;
            public CustomViewHolder(View view) {
                super(view);
                imageView = (ImageView) view.findViewById(R.id.imgview);
                textView = (TextView) view.findViewById(R.id.title);
                textView2 = (TextView) view.findViewById(R.id.name);
                textView3 = (TextView) view.findViewById(R.id.date);
                textView4 = (TextView) view.findViewById(R.id.tel);
                textView5 = (TextView) view.findViewById(R.id.location);
                textView6 = (TextView) view.findViewById(R.id.explain);
                textView7 = (TextView) view.findViewById(R.id.url);
                textView8 = (TextView) view.findViewById(R.id.userId);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), DetailActivity.class);
                        intent.putExtra("title", textView.getText().toString());
                        intent.putExtra("name", textView2.getText().toString());
                        intent.putExtra("tel", textView4.getText().toString());
                        intent.putExtra("location", textView5.getText().toString());
                        intent.putExtra("explain", textView6.getText().toString());
                        intent.putExtra("url", textView7.getText().toString());
                        intent.putExtra("userid", textView8.getText().toString());
                        startActivity(intent);
                    }
                });
            }
        }
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View view) {
        if (view == LostRegister) {
            Intent intent = new Intent(LostActivity.this, LostRegisterActivity.class);
            startActivity(intent);
        }
        if (view == campus) {
            int backColor = getResources().getColor(R.color.colorAccent);
            int backColor2 = getResources().getColor(R.color.noselect);
            campus.setBackgroundColor(backColor);
            list.setBackgroundColor(backColor2);
            campusLayout.setVisibility(View.VISIBLE);
            listLayout.setVisibility(View.GONE);
//            Intent intent = new Intent(LostActivity.this, campus.class);
//            startActivity(intent);
        }
        if (view == list){
            int backColor = getResources().getColor(R.color.colorAccent);
            int backColor2 = getResources().getColor(R.color.noselect);
            campus.setBackgroundColor(backColor2);
            list.setBackgroundColor(backColor);
            campusLayout.setVisibility(View.GONE);
            listLayout.setVisibility(View.VISIBLE);
        }
        if(view == move){
            Intent intent = new Intent(this,location_lost_Activity.class);
            intent.putExtra("location",spinner.getSelectedItem().toString());
            startActivity(intent);
        }
    }
}
