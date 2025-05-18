package com.example.dinetime.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;
import java.util.List;

@Dao
public interface ReservaDao {
    @Insert
    long insert(Reserva reserva);

    @Update
    void update(Reserva reserva);

    @Delete
    void delete(Reserva reserva);

    @Query("SELECT * FROM reservas WHERE id = :id")
    Reserva getById(int id);

    @Query("SELECT * FROM reservas")
    List<Reserva> getAll();

    @Query("SELECT * FROM reservas WHERE usuarioId = :usuarioId")
    List<Reserva> getByUsuarioId(int usuarioId);

    @Query("SELECT * FROM reservas WHERE restauranteId = :restauranteId")
    List<Reserva> getByRestauranteId(int restauranteId);

}
