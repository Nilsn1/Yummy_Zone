package com.nilscreation.yummyzone.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.nilscreation.yummyzone.FoodListActivity;
import com.nilscreation.yummyzone.Models.CategoryModel;
import com.nilscreation.yummyzone.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    public CategoryAdapter(ArrayList<CategoryModel> categorylist, Context context) {
        this.categorylist = categorylist;
        this.context = context;
    }

    ArrayList<CategoryModel> categorylist;
    Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CategoryModel category = categorylist.get(position);

        holder.categoryImg.setImageResource(categorylist.get(position).getImg());
        holder.categoryTitle.setText(categorylist.get(position).getTitle());

        switch (position) {
            case 0:
                holder.categoryBack.setCardBackgroundColor(ContextCompat.getColor(context, R.color.cat1));
                break;
            case 1:
                holder.categoryBack.setCardBackgroundColor(ContextCompat.getColor(context, R.color.cat2));
                break;
            case 2:
                holder.categoryBack.setCardBackgroundColor(ContextCompat.getColor(context, R.color.cat3));
                break;
            case 3:
                holder.categoryBack.setCardBackgroundColor(ContextCompat.getColor(context, R.color.cat4));
                break;
            case 4:
                holder.categoryBack.setCardBackgroundColor(ContextCompat.getColor(context, R.color.cat5));

        }

        holder.categoryBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FoodListActivity.class);
                intent.putExtra("category", category.getTitle());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return categorylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView categoryBack;
        ImageView categoryImg;
        TextView categoryTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryImg = itemView.findViewById(R.id.categoryImg);
            categoryTitle = itemView.findViewById(R.id.categoryTitle);
            categoryBack = itemView.findViewById(R.id.categoryBack);
        }
    }
}
