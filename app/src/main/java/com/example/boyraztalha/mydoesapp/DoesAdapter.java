package com.example.boyraztalha.mydoesapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class DoesAdapter extends RecyclerView.Adapter<DoesAdapter.MyViewHolder> {

    Context context;
    ArrayList<MyDoes> arrayList;

    public DoesAdapter(Context context, ArrayList<MyDoes> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_for_items,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.titledoes.setText(arrayList.get(i).getTitledoes());
        myViewHolder.descdoes.setText(arrayList.get(i).getDescdoes());
        myViewHolder.datedoes.setText(arrayList.get(i).getDatedoes());

        final String getTitleDoes = arrayList.get(i).getTitledoes();
        final String getDescDoes = arrayList.get(i).getDescdoes();
        final String getDateDoes = arrayList.get(i).getDatedoes();
        final String getKeyDoes = arrayList.get(i).getKeydoes();

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,EditDoesActivity.class);
                intent.putExtra("titledoes",getTitleDoes);
                intent.putExtra("descdoes",getDescDoes);
                intent.putExtra("datedoes",getDateDoes);
                intent.putExtra("keydoes",getKeyDoes);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titledoes, datedoes, descdoes;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            titledoes = itemView.findViewById(R.id.Maintitle);
            datedoes = itemView.findViewById(R.id.Datetitle);
            descdoes = itemView.findViewById(R.id.Subtitle);
        }
    }

}
