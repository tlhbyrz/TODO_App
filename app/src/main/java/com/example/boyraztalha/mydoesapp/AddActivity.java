package com.example.boyraztalha.mydoesapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class AddActivity extends AppCompatActivity {

    TextView title,texttitle,textdesc,textdate;
    EditText edittitle,editdesc,editdate;
    Button btncreate,btncancel;

    Typeface Mmedium,Mlight;

    DatabaseReference reference;
    Integer doesNum = new Random().nextInt();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        title = findViewById(R.id.textAdd);
        texttitle = findViewById(R.id.texttitle);
        textdesc = findViewById(R.id.textdesc);
        textdate = findViewById(R.id.textdate);
        edittitle = findViewById(R.id.edittitle);
        editdesc = findViewById(R.id.editdesc);
        editdate = findViewById(R.id.editdate);
        btncreate = findViewById(R.id.btnAdd);
        btncancel = findViewById(R.id.btnCancel);

        Mmedium = Typeface.createFromAsset(getAssets(),"fonts/MontserratMedium.ttf");
        Mlight = Typeface.createFromAsset(getAssets(),"fonts/MontserratLight.ttf");

        texttitle.setTypeface(Mlight);
        textdesc.setTypeface(Mlight);
        textdate.setTypeface(Mlight);

        edittitle.setTypeface(Mmedium);
        title.setTypeface(Mmedium);
        editdesc.setTypeface(Mmedium);
        editdate.setTypeface(Mmedium);

        btncreate.setTypeface(Mmedium);
        btncancel.setTypeface(Mlight);


        btncreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("tiklama yapildi");
                //insert all information to the database
                reference = FirebaseDatabase.getInstance().getReference().child("DoesApp").child("Does" + doesNum);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        System.out.println("buraya girmiyor");
                        dataSnapshot.getRef().child("titledoes").setValue(edittitle.getText().toString());
                        dataSnapshot.getRef().child("descdoes").setValue(editdesc.getText().toString());
                        dataSnapshot.getRef().child("datedoes").setValue(editdate.getText().toString());

                        Intent i = new Intent(AddActivity.this,MainActivity.class);
                        startActivity(i);
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(AddActivity.this, "Can not inserted to database!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
}
