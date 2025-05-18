package com.example.dinetime.database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import java.util.Date;

@Entity(
    tableName = "resenas",
    foreignKeys = {
        @ForeignKey(
            entity = Usuario.class,
            parentColumns = "id",
            childColumns = "usuarioId",
            onDelete = ForeignKey.CASCADE
        ),
        @ForeignKey(
            entity = Restaurante.class,
            parentColumns = "id",
            childColumns = "restauranteId",
            onDelete = ForeignKey.CASCADE
        )
    }
)
public class Reseña {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int usuarioId;
    public int restauranteId;
    public int puntuacion;
    public String comentario;
    public Date fecha;
}
