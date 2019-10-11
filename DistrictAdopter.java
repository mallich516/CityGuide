package com.example.loginpage.Home;

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

import com.example.loginpage.R;
import com.example.loginpage.Top5cities.Topcitys;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DistrictAdopter extends RecyclerView.Adapter<DistrictAdopter.DistrictViewHolder> {

    public Context context;
    ArrayList<DistrictModel> arrayList;


    public DistrictAdopter(Context context, ArrayList<DistrictModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public DistrictViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.homeitem,viewGroup,false);
        return new DistrictViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final DistrictViewHolder districtViewHolder, final int i) {


        Picasso.with(context)
                .load(arrayList.get(i).getImage())
                .placeholder(R.drawable.logo)
                .into(districtViewHolder.hi);

        districtViewHolder.ht.setText(arrayList.get(i).getDist_title());
        districtViewHolder.hc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putInt("key",i);

                Intent intent = new Intent(context, Topcitys.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
    return arrayList.size();
    }

    public class DistrictViewHolder extends RecyclerView.ViewHolder {

        ImageView hi;
        TextView ht;
        CardView hc;


        public DistrictViewHolder(@NonNull View itemView) {
            super(itemView);

            hc = itemView.findViewById(R.id.homecard);
            ht = itemView.findViewById(R.id.hometitle);
            hi = itemView.findViewById(R.id.homeimage);

        }
    }
}
