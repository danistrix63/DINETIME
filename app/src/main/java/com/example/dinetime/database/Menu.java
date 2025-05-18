package com.example.dinetime.database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "menus",
    foreignKeys = @ForeignKey(
        entity = Restaurante.class,
        parentColumns = "id",
        childColumns = "restauranteId",
        onDelete = ForeignKey.CASCADE
    )
)
public class Menu {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int restauranteId;
    public String nombre;
    public String descripcion;
    public double precio;
}
