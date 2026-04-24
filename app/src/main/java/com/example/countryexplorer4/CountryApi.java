package com.example.countryexplorer4;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CountryApi {
    // On demande uniquement les champs utilisés pour réduire la taille de la réponse
    @GET("all?fields=name,flags,capital,region,population,cca3,languages,currencies")
    Call<List<Country>> getAllCountries();
}

