package com.nilscreation.yummyzone.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nilscreation.yummyzone.CartActivity;
import com.nilscreation.yummyzone.Models.FoodModel;
import com.nilscreation.yummyzone.R;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    Context context;
    ArrayList<FoodModel> cartlist;
    int mqty, mfinalPrice;

    public CartAdapter(Context context, ArrayList<FoodModel> cartlist) {
        this.context = context;
        this.cartlist = cartlist;
    }

    public void sendDataToActivity(ArrayList<FoodModel> dataList) {
        if (context instanceof CartActivity) {
            CartActivity activity = (CartActivity) context;
            activity.receiveDataFromAdapter(dataList);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        FoodModel food = cartlist.get(position);

        Glide.with(context).load(food.getImageUrl()).placeholder(R.drawable.progress_bar).into(holder.cartImg);
        holder.cartTitle.setText(food.getTitle());
        holder.cartQty.setText(String.valueOf(food.getQty()));
        holder.cartfoodPrice.setText(String.valueOf(food.getFinalPrice()));
        holder.singlePrice.setText(String.valueOf(food.getPrice()));

        sendDataToActivity(cartlist);

        holder.plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mqty = Integer.parseInt(holder.cartQty.getText().toString()) + 1;
                holder.cartQty.setText(String.valueOf(mqty));

                food.setQty(mqty);

                mfinalPrice = mqty * food.getPrice();
                holder.cartfoodPrice.setText(String.valueOf(mfinalPrice));
//                calculateTotalPrice();
                sendDataToActivity(cartlist);
            }
        });

        holder.minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(holder.cartQty.getText().toString()) > 1) {
                    mqty = Integer.parseInt(holder.cartQty.getText().toString()) - 1;
                    holder.cartQty.setText(String.valueOf(mqty));

                    food.setQty(mqty);

                    mfinalPrice = mqty * food.getPrice();
                    holder.cartfoodPrice.setText(String.valueOf(mfinalPrice));
//                    calculateTotalPrice();
                    sendDataToActivity(cartlist);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView cartImg, plusBtn, minusBtn;
        TextView cartTitle, cartQty, singlePrice, cartfoodPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cartImg = itemView.findViewById(R.id.cartImg);
            plusBtn = itemView.findViewById(R.id.plusBtn);
            minusBtn = itemView.findViewById(R.id.minusBtn);
            cartTitle = itemView.findViewById(R.id.cartfoodTitle);
            cartQty = itemView.findViewById(R.id.cartQty);
            singlePrice = itemView.findViewById(R.id.singlePrice);
            cartfoodPrice = itemView.findViewById(R.id.cartfoodPrice);
        }
    }
}
