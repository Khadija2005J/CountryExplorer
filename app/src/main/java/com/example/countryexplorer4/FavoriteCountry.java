package com.example.countryexplorer4;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorites")
public class FavoriteCountry {
    @PrimaryKey
    @NonNull
    private String cca3;
    private String name;
    private String flagUrl;
    private String region;

    public FavoriteCountry(@NonNull String cca3, String name, String flagUrl, String region) {
        this.cca3 = cca3;
        this.name = name;
        this.flagUrl = flagUrl;
        this.region = region;
    }

    @NonNull
    public String getCca3() { return cca3; }
    public String getName() { return name; }
    public String getFlagUrl() { return flagUrl; }
    public String getRegion() { return region; }
}
