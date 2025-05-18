package com.example.dinetime.database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "restaurantes",
    foreignKeys = @ForeignKey(
        entity = Usuario.class,
        parentColumns = "id",
        childColumns = "propietarioId",
        onDelete = ForeignKey.SET_NULL
    )
)
public class Restaurante {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String nombre;
    public String direccion;
    public String telefono;
    public String horario;
    public Integer capacidad;
    public Integer propietarioId;
}
