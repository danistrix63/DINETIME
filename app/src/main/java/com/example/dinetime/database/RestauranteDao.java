package com.example.dinetime.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;
import java.util.List;

@Dao
public interface RestauranteDao {
    @Insert
    long insert(Restaurante restaurante);

    @Update
    void update(Restaurante restaurante);

    @Delete
    void delete(Restaurante restaurante);

    @Query("SELECT * FROM restaurantes WHERE id = :id")
    Restaurante getById(int id);

    @Query("SELECT * FROM restaurantes")
    List<Restaurante> getAll();

    @Query("SELECT * FROM restaurantes WHERE nombre = :nombre LIMIT 1")
    Restaurante getByName(String nombre);
}
