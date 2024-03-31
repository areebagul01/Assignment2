package com.example.assignment2;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ResturantAdapter extends RecyclerView.Adapter<ResturantAdapter.RestaurantViewHolder>{

    public ArrayList<Restaurant> restaurants;

    public ResturantAdapter(ArrayList<Restaurant> list) {
        restaurants = list;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.resturant_card, parent, false);
        return new RestaurantViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        holder.name.setText(restaurants.get(position).getName());
        holder.location.setText(restaurants.get(position).getLocation());
        holder.phone.setText(restaurants.get(position).getPhone());
        holder.description.setText(restaurants.get(position).getDescription());
        holder.rating.setText(restaurants.get(position).getRating().toString());
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    public static class RestaurantViewHolder extends RecyclerView.ViewHolder {
        TextView name, location, phone, description, rating;
        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            name =  itemView.findViewById(R.id.tv_name);
            location =  itemView.findViewById(R.id.tv_location);
            phone =  itemView.findViewById(R.id.tv_phone);
            description =  itemView.findViewById(R.id.tv_description);
            rating =  itemView.findViewById(R.id.tv_rating);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
