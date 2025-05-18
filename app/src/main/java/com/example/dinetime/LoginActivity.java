package com.example.dinetime;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dinetime.R;
import com.example.dinetime.RegisterActivity;
import com.example.dinetime.ForgotPasswordActivity;
import com.example.dinetime.database.AppDatabase;
import com.example.dinetime.database.Usuario;
import com.example.dinetime.database.UsuarioDao;

public class LoginActivity extends AppCompatActivity {

    private UsuarioDao usuarioDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usuarioDao = AppDatabase.getInstance(this).usuarioDao();

        EditText editTextEmail = findViewById(R.id.etEmail);
        EditText editTextPassword = findViewById(R.id.etPassword);
        Button btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        TextView tvCrearCuenta = findViewById(R.id.tvCrearCuenta);
        TextView tvOlvidarContrasena = findViewById(R.id.tvOlvidarPassword);

        tvCrearCuenta.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        tvOlvidarContrasena.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });

        btnIniciarSesion.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            new Thread(() -> {
                Usuario user = usuarioDao.getByEmail(email);
                runOnUiThread(() -> {
                    if (user != null && user.passwordHash.equals(password)) {
                        // Save user info in SharedPreferences
                        SharedPreferences userPrefs = getSharedPreferences("user_session", MODE_PRIVATE);
                        SharedPreferences.Editor editor = userPrefs.edit();
                        editor.putString("user_email", user.email);
                        editor.apply();

                        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                    }
                });
            }).start();
        });
    }
}
