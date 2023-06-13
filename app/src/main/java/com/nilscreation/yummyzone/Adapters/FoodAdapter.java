package com.nilscreation.yummyzone.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nilscreation.yummyzone.DetailActivity;
import com.nilscreation.yummyzone.Models.FoodModel;
import com.nilscreation.yummyzone.R;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

    Context context;
    ArrayList<FoodModel> foodlist;

    public FoodAdapter(Context context, ArrayList<FoodModel> foodlist) {
        this.context = context;
        this.foodlist = foodlist;
    }

    @NonNull
    @Override
    public FoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.food_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodAdapter.ViewHolder holder, int position) {

        FoodModel food = foodlist.get(position);

        holder.title.setText(food.getTitle());
        holder.description.setText(food.getDescription());
        holder.price.setText(String.valueOf(food.getPrice()));
        Glide.with(context).load(food.getImageUrl()).into(holder.productImg);

        holder.foodlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("Category", food.getCategory());
                intent.putExtra("Title", food.getTitle());
                intent.putExtra("Description", food.getDescription());
                intent.putExtra("Price", food.getPrice());
                intent.putExtra("ImageUrl", food.getImageUrl());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return foodlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, price;
        ConstraintLayout foodlayout;

        ImageView productImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            foodlayout = itemView.findViewById(R.id.foodlayout);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            price = itemView.findViewById(R.id.productprice);
            productImg = itemView.findViewById(R.id.productImg);
        }
    }
}
