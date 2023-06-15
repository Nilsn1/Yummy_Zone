package com.nilscreation.yummyzone.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nilscreation.yummyzone.DetailActivity;
import com.nilscreation.yummyzone.Models.FoodModel;
import com.nilscreation.yummyzone.Models.PopularModel;
import com.nilscreation.yummyzone.R;

import java.util.ArrayList;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder> {

    Context context;
    ArrayList<FoodModel> foodlist;

    public PopularAdapter(Context context, ArrayList<FoodModel> foodlist) {
        this.context = context;
        this.foodlist = foodlist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.popular_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        FoodModel food = foodlist.get(position);

        holder.title.setText(food.getTitle());
        holder.price.setText(String.valueOf(food.getPrice()));
        Glide.with(context).load(food.getImageUrl()).into(holder.imageView);

        holder.cardBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("Category", food.getCategory());
                intent.putExtra("Title", food.getTitle());
                intent.putExtra("Description", food.getDescription());
                intent.putExtra("Price", food.getPrice());
                intent.putExtra("ImageUrl", food.getImageUrl());
                intent.putExtra("Delivery", food.getDeliveryCharges());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return foodlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView title, price;
        CardView cardBack;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.mimageview);
            title = itemView.findViewById(R.id.mTitle);
            price = itemView.findViewById(R.id.mprice);
            cardBack = itemView.findViewById(R.id.cardBack);
        }
    }

}
