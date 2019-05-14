package com.example.productsearchapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.productsearchapp.R;
import com.example.productsearchapp.pojo.DetailItemSimilar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SimilarRecyclerViewAdapter extends RecyclerView.Adapter<SimilarRecyclerViewAdapter.ViewHolder> {
    private ArrayList<DetailItemSimilar> dataset;
    private Context context;

    // ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final View itemView;
        private ImageView image;
        private TextView title;
        private TextView shipping_cost;
        private TextView shipping_day;
        private TextView price;

        public ViewHolder(View view) {
            super(view);
            itemView = view;
            image = (ImageView) view.findViewById(R.id.image);
            title = (TextView) view.findViewById(R.id.title);
            shipping_cost = (TextView) view.findViewById(R.id.shipping_cost);
            shipping_day = (TextView) view.findViewById(R.id.shipping_day);
            price = (TextView) view.findViewById(R.id.price);
        }
    }

    public SimilarRecyclerViewAdapter(ArrayList<DetailItemSimilar> dataset) {
        this.dataset = dataset;
    }

    @Override
    public SimilarRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_item_similar, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // data -> view
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        context = holder.title.getContext();
        Picasso.with(context).load(dataset.get(position).getImageUrl()).into(holder.image);
        holder.title.setText(dataset.get(position).getTitle());

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(dataset.get(position).getEbayUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
            }
        });

        holder.shipping_cost.setText(dataset.get(position).getShippingCost());
        holder.shipping_day.setText(dataset.get(position).getShippingDay());
        holder.price.setText(dataset.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}


