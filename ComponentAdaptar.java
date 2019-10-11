package com.example.loginpage.components;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.loginpage.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ComponentAdaptar extends RecyclerView.Adapter<ComponentAdaptar.Component> {
    private Context context;
    ArrayList<ComponentModel> arrayList;

    public ComponentAdaptar(Context context, ArrayList<ComponentModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public Component onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.componentitem,viewGroup,false);
        return new Component(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Component component, final int i) {

        component.title.setText(arrayList.get(i).getTitle());
        component.desc.setText(arrayList.get(i).getDesc());
        component.rating.setText(arrayList.get(i).getRating());
        Picasso.with(context)
                .load(arrayList.get(i).getImage())
                .placeholder(R.drawable.logo)
                .into(component.iv);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class Component extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView title,desc,rating;


        public Component(@NonNull View itemView) {
            super(itemView);

            iv = itemView.findViewById(R.id.componentimage);
            title = itemView.findViewById(R.id.componenttitle);
            desc = itemView.findViewById(R.id.componentdesc);
            rating = itemView.findViewById(R.id.componentrating);
        }
    }
}
