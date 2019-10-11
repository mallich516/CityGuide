package com.example.loginpage.city;

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
import com.example.loginpage.service.ServiceActivity;

public class CityAdaptar extends RecyclerView.Adapter<CityAdaptar.Adaptar> {

    Context context;
    int images[];
    String names[];

    public CityAdaptar(Context context, int[] images, String[] names) {
        this.context = context;
        this.images = images;
        this.names = names;
    }

    @NonNull
    @Override
    public Adaptar onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(context).inflate(R.layout.cityitem,viewGroup,false);
        return new Adaptar(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptar adaptar, final int i) {

        adaptar.iv.setImageResource(images[i]);
        adaptar.tv.setText(names[i]);

        adaptar.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("hotel",i);
                Intent i = new Intent(context, ServiceActivity.class);
                i.putExtras(bundle);
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return names.length;
    }

    public class Adaptar extends RecyclerView.ViewHolder {

        ImageView iv;
        TextView tv;
        CardView cv;

        public Adaptar(@NonNull View itemView) {
            super(itemView);

            iv = itemView.findViewById(R.id.cityserviceicons);
            tv = itemView.findViewById(R.id.cityservices);
            cv = itemView.findViewById(R.id.citycardview);

        }
    }
}
