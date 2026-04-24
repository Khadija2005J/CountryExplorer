package com.example.countryexplorer4;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    private AppDatabase db;
    private boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        db = AppDatabase.getInstance(this);

        ImageView imgFlag = findViewById(R.id.imgFlagDetail);
        TextView txtName = findViewById(R.id.txtNameDetail);
        TextView txtCapital = findViewById(R.id.txtCapitalDetail);
        TextView txtRegion = findViewById(R.id.txtRegionDetail);
        TextView txtPopulation = findViewById(R.id.txtPopulationDetail);
        TextView txtLanguages = findViewById(R.id.txtLanguagesDetail);
        TextView txtCurrencies = findViewById(R.id.txtCurrenciesDetail);
        Button btnFavorite = findViewById(R.id.btnFavorite);
        Button btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> finish());

        Country country = (Country) getIntent().getSerializableExtra("country");

        if (country != null) {
            Glide.with(this).load(country.getFlagUrl()).into(imgFlag);
            txtName.setText(country.getCommonName());
            txtCapital.setText("Capitale : " + country.getCapital());
            txtRegion.setText("Région : " + country.getRegion());
            txtPopulation.setText("Population : " + country.getPopulation());
            txtLanguages.setText("Langues : " + country.getLanguages());
            txtCurrencies.setText("Monnaies : " + country.getCurrencies());

            // Check if already favorite
            FavoriteCountry fav = db.countryDao().getFavoriteByCode(country.getCca3());
            if (fav != null) {
                isFavorite = true;
                btnFavorite.setText("Retirer des favoris");
            }

            btnFavorite.setOnClickListener(v -> {
                if (isFavorite) {
                    db.countryDao().deleteFavorite(new FavoriteCountry(country.getCca3(), country.getCommonName(), country.getFlagUrl(), country.getRegion()));
                    btnFavorite.setText("Ajouter aux favoris");
                    isFavorite = false;
                    Toast.makeText(DetailActivity.this, "Supprimé des favoris", Toast.LENGTH_SHORT).show();
                } else {
                    db.countryDao().insertFavorite(new FavoriteCountry(country.getCca3(), country.getCommonName(), country.getFlagUrl(), country.getRegion()));
                    btnFavorite.setText("Retirer des favoris");
                    isFavorite = true;
                    Toast.makeText(DetailActivity.this, "Ajouté aux favoris", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
