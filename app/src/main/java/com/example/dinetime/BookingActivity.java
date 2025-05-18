package com.example.dinetime;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dinetime.database.AppDatabase;
import com.example.dinetime.database.Reserva;
import com.example.dinetime.database.Restaurante;
import com.example.dinetime.database.Usuario;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingActivity extends AppCompatActivity {

    private ReservaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        RecyclerView recyclerView = findViewById(R.id.reservasRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ReservaAdapter(new ArrayList<>(), new HashMap<>(), getImagenesRestaurantes());
        recyclerView.setAdapter(adapter);

        findViewById(R.id.btnReservarMas).setOnClickListener(v -> {
            startActivity(new Intent(this, ExploreActivity.class));
        });

        loadReservas();

        // Highlight the reservations tab in bottom navigation
        BottomNavUtil.setup(this, R.id.nav_reservations);
    }

    private void loadReservas() {
        SharedPreferences userPrefs = getSharedPreferences("user_session", MODE_PRIVATE);
        String userEmail = userPrefs.getString("user_email", "");
        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(this);
            Usuario usuario = db.usuarioDao().getByEmail(userEmail);
            if (usuario == null) return;

            List<Reserva> reservas = db.reservaDao().getByUsuarioId(usuario.id);
            List<Restaurante> restaurantes = db.restauranteDao().getAll();
            Map<Integer, Restaurante> restauranteMap = new HashMap<>();
            for (Restaurante r : restaurantes) {
                restauranteMap.put(r.id, r);
            }
            runOnUiThread(() -> adapterUpdate(reservas, restauranteMap));
        }).start();
    }

    private void adapterUpdate(List<Reserva> reservas, Map<Integer, Restaurante> restauranteMap) {
        adapter = new ReservaAdapter(reservas, restauranteMap, getImagenesRestaurantes());
        RecyclerView recyclerView = findViewById(R.id.reservasRecyclerView);
        recyclerView.setAdapter(adapter);
    }

    private Map<String, Integer> getImagenesRestaurantes() {
        Map<String, Integer> map = new HashMap<>();
        map.put("Muysca", R.drawable.restaurante1);
        map.put("Restaurante Sol", R.drawable.restaurante2);
        map.put("La Taqueria", R.drawable.restaurante3);
        // Add more if needed
        return map;
    }
}
