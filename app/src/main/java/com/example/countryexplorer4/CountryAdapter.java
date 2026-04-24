package com.example.countryexplorer4;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {

    private List<Country> countries;
    private List<Country> countriesFull;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Country country);
    }

    public CountryAdapter(List<Country> countries, OnItemClickListener listener) {
        this.countries = countries;
        this.countriesFull = new ArrayList<>(countries);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_country, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Country country = countries.get(position);
        holder.txtName.setText(country.getCommonName());
        holder.txtRegion.setText(country.getRegion());
        Glide.with(holder.itemView.getContext())
                .load(country.getFlagUrl())
                .into(holder.imgFlag);
        
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(country);
            }
        });
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    public void filter(String text) {
        countries.clear();
        if (text.isEmpty()) {
            countries.addAll(countriesFull);
        } else {
            text = text.toLowerCase();
            for (Country country : countriesFull) {
                if (country.getCommonName().toLowerCase().contains(text)) {
                    countries.add(country);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void filterByRegion(String region) {
        countries.clear();
        if (region.equals("Tous")) {
            countries.addAll(countriesFull);
        } else {
            for (Country country : countriesFull) {
                if (country.getRegion().equalsIgnoreCase(region)) {
                    countries.add(country);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void updateList(List<Country> newList) {
        this.countries = newList;
        this.countriesFull = new ArrayList<>(newList);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFlag;
        TextView txtName, txtRegion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFlag = itemView.findViewById(R.id.imgFlag);
            txtName = itemView.findViewById(R.id.txtName);
            txtRegion = itemView.findViewById(R.id.txtRegion);
        }
    }
}
