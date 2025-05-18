package com.example.dinetime;

import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.view.MenuItem;
import android.widget.PopupMenu;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Header icons
        ImageView menuIcon = findViewById(R.id.menuIcon);
        ImageView userIcon = findViewById(R.id.userIcon);

        // Search bar
        EditText searchBar = findViewById(R.id.searchBar);

        // "Ver Todos"
        TextView verTodos = findViewById(R.id.verTodos);

        // Banner "Pedir" buttons
        TextView pedirBanner1 = findViewById(R.id.btnPedirBanner1);
        TextView pedirBanner2 = findViewById(R.id.btnPedirBanner2);

        if (pedirBanner1 != null) {
            pedirBanner1.setOnClickListener(v -> startActivity(new Intent(this, ExploreActivity.class)));
        }
        if (pedirBanner2 != null) {
            pedirBanner2.setOnClickListener(v -> startActivity(new Intent(this, ExploreActivity.class)));
        }

        // Restaurant cards (with unique IDs)
        View cardRestaurante = findViewById(R.id.card_restaurante1);
        View cardRestaurante2 = findViewById(R.id.card_restaurante2);
        View cardRestaurante3 = findViewById(R.id.card_restaurante3);

        if (cardRestaurante != null) {
            cardRestaurante.setOnClickListener(v -> {
                Intent intent = new Intent(this, DetailsActivity.class);
                intent.putExtra("name", "Muysca");
                intent.putExtra("location", "Carrer del Clot, 135");
                intent.putExtra("imageResId", R.drawable.restaurante1);
                startActivity(intent);
            });
        }
        if (cardRestaurante2 != null) {
            cardRestaurante2.setOnClickListener(v -> {
                Intent intent = new Intent(this, DetailsActivity.class);
                intent.putExtra("name", "Restaurante Sol");
                intent.putExtra("location", "Carrer del Clot, 127");
                intent.putExtra("imageResId", R.drawable.restaurante2);
                startActivity(intent);
            });
        }
        if (cardRestaurante3 != null) {
            cardRestaurante3.setOnClickListener(v -> {
                Intent intent = new Intent(this, DetailsActivity.class);
                intent.putExtra("name", "La Taqueria");
                intent.putExtra("location", "Carrer de Pujades, 172");
                intent.putExtra("imageResId", R.drawable.restaurante3);
                startActivity(intent);
            });
        }

        // "Reservar" button in Muysca card
        Button reservarMuysca = findViewById(R.id.btnReservarMuysca);
        if (reservarMuysca != null) {
            reservarMuysca.setOnClickListener(v -> {
                Intent intent = new Intent(this, DetailsActivity.class);
                intent.putExtra("name", "Muysca");
                intent.putExtra("location", "Carrer del Clot, 135");
                intent.putExtra("imageResId", R.drawable.restaurante1);
                startActivity(intent);
            });
        }

        // Menu icon (show popup menu)
        if (menuIcon != null) {
            menuIcon.setOnClickListener(v -> {
                PopupMenu popup = new PopupMenu(this, menuIcon);
                popup.getMenu().add(0, 1, 0, "Historial de Reservas");
                popup.getMenu().add(0, 2, 1, "Cancelar Reserva");
                popup.getMenu().add(0, 3, 2, "Explorar Restaurantes");
                popup.getMenu().add(0, 4, 3, "Perfil");
                popup.setOnMenuItemClickListener(item -> {
                    switch (item.getItemId()) {
                        case 1:
                            startActivity(new Intent(this, BookingActivity.class));
                            return true;
                        case 2:
                            startActivity(new Intent(this, CancelActivity.class));
                            return true;
                        case 3:
                            startActivity(new Intent(this, ExploreActivity.class));
                            return true;
                        case 4:
                            startActivity(new Intent(this, ProfileActivity.class));
                            return true;
                        default:
                            return false;
                    }
                });
                popup.show();
            });
        }

        // User icon (go to profile)
        if (userIcon != null) {
            userIcon.setOnClickListener(v -> startActivity(new Intent(this, ProfileActivity.class)));
        }

        // Search bar: handle search directly here
        searchBar.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String query = searchBar.getText().toString().trim();
                // TODO: Filter your restaurant list here
                Toast.makeText(this, "Searching for: " + query, Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });

        // "Ver Todos"
        verTodos.setOnClickListener(v -> startActivity(new Intent(this, ExploreActivity.class)));

        // Bottom navigation (use correct IDs from your bottom_navigation.xml)
        View navHome = findViewById(R.id.nav_home);
        View navReservations = findViewById(R.id.nav_reservations);
        View navProfile = findViewById(R.id.nav_profile);

        if (navHome != null) {
            navHome.setOnClickListener(v -> {
                // Already in Home
            });
        }
        if (navReservations != null) {
            navReservations.setOnClickListener(v -> startActivity(new Intent(this, BookingActivity.class)));
        }
        if (navProfile != null) {
            navProfile.setOnClickListener(v -> startActivity(new Intent(this, ProfileActivity.class)));
        }
    }
}
