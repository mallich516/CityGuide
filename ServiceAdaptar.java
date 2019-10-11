package com.example.loginpage.service;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginpage.R;
import com.example.loginpage.Top5cities.Top5Model;
import com.example.loginpage.components.ComponentActivity;
import com.example.loginpage.components.ComponentModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ServiceAdaptar extends RecyclerView.Adapter<ServiceAdaptar.SAdaptar> {

    Context context;
    ArrayList<Top5Model> arrayList;

    public ServiceAdaptar(Context context, ArrayList<Top5Model> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public SAdaptar onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.servicesitem, viewGroup, false);
        return new SAdaptar(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SAdaptar sAdaptar, final int i) {
        if (arrayList != null) {
            Picasso.with(context)
                    .load(arrayList.get(i).getImage())
                    .placeholder(R.drawable.logo)
                    .into(sAdaptar.iv);

            sAdaptar.tv.setText(arrayList.get(i).getTitle());
            sAdaptar.cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ComponentActivity.class);
                    intent.putExtra("index", i);
                    context.startActivity(intent);
                }
            });
        } else {
            Toast.makeText(context, "Sorry... No Data Found", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class SAdaptar extends RecyclerView.ViewHolder {

        TextView tv;
        ImageView iv;
        CardView cv;

        public SAdaptar(@NonNull View itemView) {
            super(itemView);

            tv = itemView.findViewById(R.id.servicetitle);
            iv = itemView.findViewById(R.id.serviceimage);
            cv = itemView.findViewById(R.id.servicescardview);

        }
    }
}
