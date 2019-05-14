package com.example.productsearchapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.productsearchapp.ItemDetailActivity;
import com.example.productsearchapp.R;
import com.example.productsearchapp.pojo.SearchItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearhListRecyclerViewAdapter extends RecyclerView.Adapter<SearhListRecyclerViewAdapter.ViewHolder> {
    private ArrayList<SearchItem> dataset;
    private Context context;
    // key for intent
    public static final String ITEMID = "com.example.myapplication.ITEMID";
    public static final String ITEMTITLE = "com.example.myapplication.ITEMTITLE";
    public static final String ITEMSHIPPING = "com.example.myapplication.ITEMSHIPPING";
    public static final String ITEMVIEWURL = "com.example.myapplication.ITEMVIEWURL";
    public static final String ITEMPRICE = "com.example.myapplication.ITEMPRICE";

    // ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final View itemView;
        private TextView itemId;
        private ImageView galleryUrl;
        private TextView title;
        private TextView price;
        private TextView shippingCost;
        private TextView zipCode;
        private TextView condition;

        public ViewHolder(View view) {
            super(view);
            itemView = view;
            itemId = (TextView) view.findViewById(R.id.itemId);
            galleryUrl = (ImageView) view.findViewById(R.id.galleryUrl);
            title = (TextView) view.findViewById(R.id.title);
            price = (TextView) view.findViewById(R.id.price);
            shippingCost = (TextView) view.findViewById(R.id.shipping);
            zipCode = (TextView) view.findViewById(R.id.zip);
            condition = (TextView) view.findViewById(R.id.condition);
        }
    }

    // data for viewholder
    public SearhListRecyclerViewAdapter(ArrayList<SearchItem> dataset) {
        this.dataset = dataset;
    }

    @Override
    public SearhListRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();

        return viewHolder;
    }

    // bind data into viewholder
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.itemId.setText(dataset.get(position).getItemId());
        String imageUri = dataset.get(position).getGalleryUrl();
        Picasso.with(context).load(imageUri).into(holder.galleryUrl);
        String title = dataset.get(position).getTitle();

        if (title.length()>40){
            title = title.substring(0,40);
            title += " ...";
        }
        holder.title.setText(title);

        holder.price.setText("$"+Double.toString(dataset.get(position).getPrice()));

        Double shippingCost = dataset.get(position).getShippingCost();
        String shippingCostLabel;
        if(shippingCost==0){
            shippingCostLabel = "Free Shipping";
        } else {
            shippingCostLabel = "$"+shippingCost;
        }
        holder.shippingCost.setText(shippingCostLabel);

        holder.zipCode.setText("Zip: "+ dataset.get(position).getZipCode());
        holder.condition.setText(dataset.get(position).getCondition());

        // onClickListener
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "You selected "+dataset.get(position).getTitle(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, ItemDetailActivity.class);
                intent.putExtra(ITEMID, dataset.get(position).getItemId()); // key-value pair
                intent.putExtra(ITEMTITLE, dataset.get(position).getTitle()); // key-value pair
                intent.putExtra(ITEMSHIPPING, Double.toString(dataset.get(position).getShippingCost())); // key-value pair
                intent.putExtra(ITEMVIEWURL, dataset.get(position).getViewItemURL()); // key-value pair
                intent.putExtra(ITEMPRICE, dataset.get(position).getPrice()); // key-value pair
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}


