package com.example.dinetime.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;
import java.util.List;

@Dao
public interface ReseñaDao {
    @Insert
    long insert(Reseña reseña);

    @Update
    void update(Reseña reseña);

    @Delete
    void delete(Reseña reseña);

    @Query("SELECT * FROM resenas WHERE id = :id")
    Reseña getById(int id);

    @Query("SELECT * FROM resenas")
    List<Reseña> getAll();

    @Query("SELECT * FROM resenas WHERE restauranteId = :restauranteId")
    List<Reseña> getByRestauranteId(int restauranteId);

    @Query("SELECT * FROM resenas WHERE usuarioId = :usuarioId")
    List<Reseña> getByUsuarioId(int usuarioId);
}
