package com.example.dinetime.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;
import java.util.List;

@Dao
public interface MenuDao {
    @Insert
    long insert(Menu menu);

    @Update
    void update(Menu menu);

    @Delete
    void delete(Menu menu);

    @Query("SELECT * FROM menus WHERE id = :id")
    Menu getById(int id);

    @Query("SELECT * FROM menus")
    List<Menu> getAll();

    @Query("SELECT * FROM menus WHERE restauranteId = :restauranteId")
    List<Menu> getByRestauranteId(int restauranteId);
}
