package com.example.dinetime.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Usuario.class, Restaurante.class, Reserva.class, Menu.class, Reseña.class}, version = 1)
@TypeConverters(Converters.class)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract UsuarioDao usuarioDao();
    public abstract RestauranteDao restauranteDao();
    public abstract ReservaDao reservaDao();
    public abstract MenuDao menuDao();
    public abstract ReseñaDao resenaDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "dinetime.db")
                .fallbackToDestructiveMigration()
                .build();
        }
        return instance;
    }
}
