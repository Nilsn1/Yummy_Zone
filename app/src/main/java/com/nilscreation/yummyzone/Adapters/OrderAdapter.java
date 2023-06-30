package com.nilscreation.yummyzone.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.nilscreation.yummyzone.Models.OrderDetails;
import com.nilscreation.yummyzone.OrderDetailActivity;
import com.nilscreation.yummyzone.R;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    Context context;
    ArrayList<OrderDetails> orderlist;
    FragmentActivity activity;

    public OrderAdapter(Context context, ArrayList<OrderDetails> orderlist, FragmentActivity activity) {
        this.context = context;
        this.orderlist = orderlist;
        this.activity = activity;
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {

        OrderDetails order = orderlist.get(position);
        holder.orderId.setText(order.getOrderId());
        holder.orderPrice.setText(String.valueOf(order.getOrderPrice()));
        holder.orderTime.setText(order.getTime());

        holder.orderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(activity, OrderDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("orderId", order.getOrderId());
                bundle.putString("orderTime", order.getTime());
                bundle.putString("orderAddress", order.getAddress());
                bundle.putInt("itemTotal", order.getItemsTotal());
                bundle.putInt("delivery", order.getDeliveryCharges());
                bundle.putInt("orderPrice", order.getOrderPrice());
                intent.putExtras(bundle);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return orderlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView orderId, orderPrice, orderTime;

        LinearLayout orderLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            orderId = itemView.findViewById(R.id.orderId);
            orderPrice = itemView.findViewById(R.id.orderPrice);
            orderTime = itemView.findViewById(R.id.orderTime);
            orderLayout = itemView.findViewById(R.id.orderLayout);
        }
    }
}
