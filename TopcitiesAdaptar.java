package com.example.loginpage.Top5cities;

import android.app.Dialog;
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

import com.example.loginpage.city.City;
import com.example.loginpage.R;
import com.squareup.picasso.Picasso;

import java.util.AbstractList;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class TopcitiesAdaptar extends RecyclerView.Adapter<TopcitiesAdaptar.Adaptar> {

    Context context;
    AbstractList<Top5Model> arrayList;

    public TopcitiesAdaptar(Context context, AbstractList<Top5Model> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public TopcitiesAdaptar.Adaptar onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.topcitiesitem,viewGroup,false);
        return new Adaptar(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TopcitiesAdaptar.Adaptar adaptar, final int i) {

        Picasso.with(context)
                .load(arrayList.get(i).getImage())
                .placeholder(R.drawable.logo)
                .into(adaptar.civ);

        adaptar.tv.setText(arrayList.get(i).getTitle());

        adaptar.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int ik=Topcitys.getdistindex();
                Bundle bundle = new Bundle();
                bundle.putInt("key1",i);
                bundle.putInt("inkey",ik);

                Intent intent = new Intent(context,City.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class Adaptar extends RecyclerView.ViewHolder {
        CircleImageView civ;
        TextView tv;
        CardView cv;
        public Adaptar(@NonNull View itemView) {
            super(itemView);

            tv = itemView.findViewById(R.id.topcitiestitle);
            cv = itemView.findViewById(R.id.topcitiescardview);
            civ = itemView.findViewById(R.id.topcitiesimageview);
        }
    }
}