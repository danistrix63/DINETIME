package com.example.dinetime;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dinetime.database.AppDatabase;
import com.example.dinetime.database.Reserva;
import com.example.dinetime.database.Usuario;
import com.example.dinetime.database.UsuarioDao;
import com.example.dinetime.database.Restaurante;
import com.example.dinetime.database.RestauranteDao;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ImageView imageView = findViewById(R.id.restaurantImage);
        TextView nameView = findViewById(R.id.restaurantName);
        TextView locationView = findViewById(R.id.restaurantLocation);

        int imageResId = getIntent().getIntExtra("imageResId", 0);
        String name = getIntent().getStringExtra("name");
        String location = getIntent().getStringExtra("location");

        imageView.setImageResource(imageResId);
        nameView.setText(name);
        locationView.setText(location);
        // Setup bottom navigation (no tab selected)
        BottomNavUtil.setup(this, 0);

        EditText etFechaReserva = findViewById(R.id.etFechaReserva);
        EditText etEstado = findViewById(R.id.etEstado);
        Button bookingBtn = findViewById(R.id.bookingBtn);

        bookingBtn.setOnClickListener(v -> {
            String fechaReservaStr = etFechaReserva.getText().toString().trim();
            String estado = etEstado.getText().toString().trim();

            if (fechaReservaStr.isEmpty() || estado.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Parse date
            Date fechaReserva;
            try {
                fechaReserva = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(fechaReservaStr);
            } catch (Exception e) {
                Toast.makeText(this, "Invalid date format", Toast.LENGTH_SHORT).show();
                return;
            }

            // Get user email from SharedPreferences
            SharedPreferences userPrefs = getSharedPreferences("user_session", MODE_PRIVATE);
            String userEmail = userPrefs.getString("user_email", "");

            new Thread(() -> {
                AppDatabase db = AppDatabase.getInstance(this);
                UsuarioDao usuarioDao = db.usuarioDao();
                RestauranteDao restauranteDao = db.restauranteDao();

                Usuario usuario = usuarioDao.getByEmail(userEmail);
                Restaurante restaurante = restauranteDao.getByName(name);

                // If restaurant does not exist, insert it
                if (restaurante == null) {
                    restaurante = new Restaurante();
                    restaurante.nombre = name;
                    restaurante.direccion = location;
                    long newId = restauranteDao.insert(restaurante);
                    restaurante = restauranteDao.getById((int) newId);
                }

                if (usuario == null || restaurante == null) {
                    runOnUiThread(() -> Toast.makeText(this, "User or restaurant not found", Toast.LENGTH_SHORT).show());
                    return;
                }

                Reserva reserva = new Reserva();
                reserva.usuarioId = usuario.id;
                reserva.restauranteId = restaurante.id;
                reserva.fechaReserva = fechaReserva;
                reserva.estado = estado;

                db.reservaDao().insert(reserva);

                runOnUiThread(() -> Toast.makeText(this, "Reservation saved!", Toast.LENGTH_SHORT).show());
            }).start();
        });
    }
}
