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

public class EditDoesActivity extends AppCompatActivity {

    TextView title,texttitle,textdesc,textdate;
    EditText edittitle,editdesc,editdate;
    Button btnsave,btndelete;

    Typeface Mmedium,Mlight;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_does);

        title = findViewById(R.id.textAdd);
        texttitle = findViewById(R.id.texttitle);
        textdesc = findViewById(R.id.textdesc);
        textdate = findViewById(R.id.textdate);
        edittitle = findViewById(R.id.edittitle);
        editdesc = findViewById(R.id.editdesc);
        editdate = findViewById(R.id.editdate);
        btnsave = findViewById(R.id.btnAdd);
        btndelete = findViewById(R.id.btnCancel);

        Mmedium = Typeface.createFromAsset(getAssets(),"fonts/MontserratMedium.ttf");
        Mlight = Typeface.createFromAsset(getAssets(),"fonts/MontserratLight.ttf");

        texttitle.setTypeface(Mlight);
        textdesc.setTypeface(Mlight);
        textdate.setTypeface(Mlight);

        edittitle.setTypeface(Mmedium);
        title.setTypeface(Mmedium);
        editdesc.setTypeface(Mmedium);
        editdate.setTypeface(Mmedium);

        btnsave.setTypeface(Mmedium);
        btndelete.setTypeface(Mlight);


        edittitle.setText(getIntent().getStringExtra("titledoes"));
        editdesc.setText(getIntent().getStringExtra("descdoes"));
        editdate.setText(getIntent().getStringExtra("datedoes"));

        reference = FirebaseDatabase.getInstance().getReference().child("DoesApp")
                .child("Does" + getIntent().getStringExtra("keydoes"));

        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Intent i = new Intent(EditDoesActivity.this,MainActivity.class);
                            startActivity(i);
                            finish();
                            Toast.makeText(EditDoesActivity.this, "Successfully removed!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(EditDoesActivity.this, "Error occured!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("titledoes").setValue(edittitle.getText().toString());
                        dataSnapshot.getRef().child("descdoes").setValue(editdesc.getText().toString());
                        dataSnapshot.getRef().child("datedoes").setValue(editdate.getText().toString());

                        Intent i = new Intent(EditDoesActivity.this,MainActivity.class);
                        startActivity(i);
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(EditDoesActivity.this, "Can not saved!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
