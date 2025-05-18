package com.example.dinetime;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
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

public class CancelActivity extends AppCompatActivity {

    private ReservaCancelAdapter adapter;
    private List<Reserva> reservas = new ArrayList<>();
    private Map<Integer, Restaurante> restauranteMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel);

        RecyclerView recyclerView = findViewById(R.id.reservasRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ReservaCancelAdapter(reservas, restauranteMap, getImagenesRestaurantes(), this::deleteReserva);
        recyclerView.setAdapter(adapter);

        // "Reservar más" button opens ExploreActivity
        findViewById(R.id.btnReservarMas).setOnClickListener(v -> {
            startActivity(new Intent(this, ExploreActivity.class));
        });

        loadReservas();
        // Setup bottom navigation (no tab selected)
        BottomNavUtil.setup(this, 0);
    }

    private void loadReservas() {
        SharedPreferences userPrefs = getSharedPreferences("user_session", MODE_PRIVATE);
        String userEmail = userPrefs.getString("user_email", "");
        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(this);
            Usuario usuario = db.usuarioDao().getByEmail(userEmail);
            if (usuario == null) return;

            reservas.clear();
            reservas.addAll(db.reservaDao().getByUsuarioId(usuario.id));
            List<Restaurante> restaurantes = db.restauranteDao().getAll();
            restauranteMap.clear();
            for (Restaurante r : restaurantes) {
                restauranteMap.put(r.id, r);
            }
            runOnUiThread(() -> adapter.notifyDataSetChanged());
        }).start();
    }

    private void deleteReserva(Reserva reserva) {
        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(this);
            db.reservaDao().delete(reserva);
            runOnUiThread(() -> {
                reservas.remove(reserva);
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "Reserva eliminada", Toast.LENGTH_SHORT).show();
            });
        }).start();
    }

    private Map<String, Integer> getImagenesRestaurantes() {
        Map<String, Integer> map = new HashMap<>();
        map.put("Muysca", R.drawable.restaurante1);
        map.put("Restaurante Sol", R.drawable.restaurante2);
        map.put("La Taqueria", R.drawable.restaurante3);
        return map;
    }
}
