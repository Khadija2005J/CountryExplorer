package com.example.countryexplorer4;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface CountryDao {
    @Query("SELECT * FROM favorites")
    List<FavoriteCountry> getAllFavorites();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavorite(FavoriteCountry country);

    @Delete
    void deleteFavorite(FavoriteCountry country);

    @Query("SELECT * FROM favorites WHERE cca3 = :code LIMIT 1")
    FavoriteCountry getFavoriteByCode(String code);
}
