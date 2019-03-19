package com.example.boyraztalha.mydoesapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Typeface Mmedium,Mlight;
    TextView pagetitle,subtitle,endpage;
    Button button;

    DatabaseReference reference;
    ArrayList<MyDoes> arrayList;
    DoesAdapter doesAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pagetitle = findViewById(R.id.text1);
        subtitle = findViewById(R.id.text2);
        endpage = findViewById(R.id.text3);
        button = findViewById(R.id.btn);

        Mmedium = Typeface.createFromAsset(getAssets(),"fonts/MontserratMedium.ttf");
        Mlight = Typeface.createFromAsset(getAssets(),"fonts/MontserratLight.ttf");

        pagetitle.setTypeface(Mmedium);
        subtitle.setTypeface(Mlight);
        endpage.setTypeface(Mlight);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList = new ArrayList<MyDoes>();

        reference = FirebaseDatabase.getInstance().getReference().child("DoesApp");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    MyDoes myDoes = dataSnapshot1.getValue(MyDoes.class);
                    arrayList.add(myDoes);
                }
                doesAdapter = new DoesAdapter(MainActivity.this,arrayList);
                recyclerView.setAdapter(doesAdapter);
                doesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Error ocuured", Toast.LENGTH_SHORT).show();
            }
        });

        //Toast.makeText(MainActivity.this, "arraylist" + arrayList, Toast.LENGTH_SHORT).show();
    }
}
