package com.example.dinetime;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class ExploreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);
        // Setup bottom navigation (no tab selected)
        BottomNavUtil.setup(this, 0);

        // Card 1: Muysca
        Button btn1 = findViewById(R.id.button_reservar1);
        btn1.setOnClickListener(v -> openDetails(
            R.drawable.restaurante1,
            "Muysca",
            "Carrer del Clot, 135"
        ));

        // Card 2: Restaurante Sol
        Button btn2 = findViewById(R.id.button_reservar2);
        btn2.setOnClickListener(v -> openDetails(
            R.drawable.restaurante2,
            "Restaurante Sol",
            "Carrer del Clot, 127"
        ));

        // Card 3: La Taqueria
        Button btn3 = findViewById(R.id.button_reservar3);
        btn3.setOnClickListener(v -> openDetails(
            R.drawable.restaurante3,
            "La Taqueria",
            "Carrer de Pujades, 172"
        ));
    }

    private void openDetails(int imageResId, String name, String location) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("imageResId", imageResId);
        intent.putExtra("name", name);
        intent.putExtra("location", location);
        startActivity(intent);
    }
}
