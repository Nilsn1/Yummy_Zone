package com.nilscreation.yummyzone.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nilscreation.yummyzone.Models.OrderDetails;
import com.nilscreation.yummyzone.R;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    Context context;
    ArrayList<OrderDetails> orderlist;

    public OrderAdapter(Context context, ArrayList<OrderDetails> orderlist) {
        this.context = context;
        this.orderlist = orderlist;
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

    }

    @Override
    public int getItemCount() {
        return orderlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView orderId, orderPrice, btnView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            orderId = itemView.findViewById(R.id.orderId);
            orderPrice = itemView.findViewById(R.id.orderPrice);
            btnView = itemView.findViewById(R.id.btnView);
        }
    }
}
