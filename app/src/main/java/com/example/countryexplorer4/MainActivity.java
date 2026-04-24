package com.example.countryexplorer4;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private RecyclerView recyclerView;
    private CountryAdapter adapter;
    private List<Country> countryList = new ArrayList<>();
    private EditText searchEditText;
    private AppDatabase db;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = AppDatabase.getInstance(this);
        recyclerView = findViewById(R.id.recyclerView);
        searchEditText = findViewById(R.id.searchEditText);
        progressBar = new ProgressBar(this); // Simple fallback if not in XML

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CountryAdapter(countryList, country -> {
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("country", country);
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        setupFilters();
        setupSearch();
        fetchCountries();
    }

    private void setupFilters() {
        findViewById(R.id.btnAll).setOnClickListener(v -> adapter.filterByRegion("Tous"));
        findViewById(R.id.btnAfrica).setOnClickListener(v -> adapter.filterByRegion("Africa"));
        findViewById(R.id.btnEurope).setOnClickListener(v -> adapter.filterByRegion("Europe"));
        findViewById(R.id.btnAmericas).setOnClickListener(v -> adapter.filterByRegion("Americas"));
        findViewById(R.id.btnAsia).setOnClickListener(v -> adapter.filterByRegion("Asia"));
        findViewById(R.id.btnOceania).setOnClickListener(v -> adapter.filterByRegion("Oceania"));
        findViewById(R.id.btnFavorites).setOnClickListener(v -> showFavorites());
    }

    private void showFavorites() {
        // Run on a separate thread if possible, but Database is configured for main thread here
        List<FavoriteCountry> favorites = db.countryDao().getAllFavorites();
        if (favorites.isEmpty()) {
            Toast.makeText(this, "Aucun favori", Toast.LENGTH_SHORT).show();
            return;
        }
        filterListByFavorites(favorites);
    }

    private void filterListByFavorites(List<FavoriteCountry> favorites) {
        List<Country> filtered = new ArrayList<>();
        for (Country c : countryList) {
            for (FavoriteCountry f : favorites) {
                if (c.getCca3().equals(f.getCca3())) {
                    filtered.add(c);
                    break;
                }
            }
        }
        adapter.updateList(filtered);
    }

    private void setupSearch() {
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void fetchCountries() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://restcountries.com/v3.1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CountryApi api = retrofit.create(CountryApi.class);
        Log.d(TAG, "Fetching countries...");
        api.getAllCountries().enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    countryList = response.body();
                    Log.d(TAG, "Fetched " + countryList.size() + " countries");
                    adapter.updateList(countryList);
                } else {
                    Log.e(TAG, "API Error: " + response.code());
                    Toast.makeText(MainActivity.this, "Erreur API : " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {
                Log.e(TAG, "Network Error", t);
                Toast.makeText(MainActivity.this, "Erreur réseau : " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
