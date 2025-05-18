package com.example.dinetime.database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import java.util.Date;

@Entity(
    tableName = "reservas",
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
public class Reserva {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int usuarioId;
    public int restauranteId;
    public Date fechaReserva;
    public String estado;
}
