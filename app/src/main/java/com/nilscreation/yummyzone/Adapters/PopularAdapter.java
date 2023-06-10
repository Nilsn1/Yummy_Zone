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

import com.nilscreation.yummyzone.DetailActivity;
import com.nilscreation.yummyzone.Models.PopularModel;
import com.nilscreation.yummyzone.R;

import java.util.ArrayList;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder> {

    public PopularAdapter(Context context, ArrayList<PopularModel> popularlist) {
        this.context = context;
        this.popularlist = popularlist;
    }

    Context context;
    ArrayList<PopularModel> popularlist;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.popular_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        PopularModel list = popularlist.get(position);

        holder.title.setText(popularlist.get(position).getTitle());
        holder.price.setText(popularlist.get(position).getPrice());
        holder.imageView.setImageResource(popularlist.get(position).getImage());

        holder.cardBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("Title", list.getTitle());
                bundle.putString("Price", list.getPrice());
                bundle.putString("Description", list.getDescription());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return popularlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView title, price;
        CardView cardBack;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageview);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
            cardBack = itemView.findViewById(R.id.cardBack);
        }
    }

}
