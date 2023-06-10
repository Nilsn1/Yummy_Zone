package com.nilscreation.yummyzone.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

        holder.title.setText(popularlist.get(position).getTitle());
        holder.price.setText(popularlist.get(position).getPrice());
        holder.imageView.setImageResource(popularlist.get(position).getImage());

    }

    @Override
    public int getItemCount() {
        return popularlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView title, price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageview);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
        }
    }

}
