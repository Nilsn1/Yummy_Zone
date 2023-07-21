package com.nilscreation.yummyzone.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nilscreation.yummyzone.Models.FoodModel;
import com.nilscreation.yummyzone.R;

import java.util.ArrayList;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ViewHolder> {

    Context context;
    ArrayList<FoodModel> foodlist;

    public OrderDetailAdapter(Context context, ArrayList<FoodModel> foodlist) {
        this.context = context;
        this.foodlist = foodlist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        FoodModel food = foodlist.get(position);

        Glide.with(context).load(food.getImageUrl()).placeholder(R.drawable.progress_bar).into(holder.orderImg);
        holder.orderTitle.setText(food.getTitle());
        holder.orderQty.setText(food.getQty() + " Qty");
        holder.orderSinglePrice.setText(String.valueOf(food.getPrice()));
        holder.foodQtyPrice.setText(String.valueOf(food.getFinalPrice()));
    }

    @Override
    public int getItemCount() {
        return foodlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView orderImg;
        TextView orderTitle, orderQty, orderSinglePrice, foodQtyPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            orderImg = itemView.findViewById(R.id.orderImg);
            orderTitle = itemView.findViewById(R.id.orderTitle);
            orderQty = itemView.findViewById(R.id.orderQty);
            orderSinglePrice = itemView.findViewById(R.id.orderSinglePrice);
            foodQtyPrice = itemView.findViewById(R.id.foodQtyPrice);
        }
    }
}
