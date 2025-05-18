package com.example.dinetime;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dinetime.database.AppDatabase;
import com.example.dinetime.database.Usuario;
import com.example.dinetime.database.UsuarioDao;

public class RegisterActivity extends AppCompatActivity {

    private UsuarioDao usuarioDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usuarioDao = AppDatabase.getInstance(this).usuarioDao();

        EditText etNombre = findViewById(R.id.etNombre);
        EditText etEmail = findViewById(R.id.etEmail);
        EditText etPassword = findViewById(R.id.etPassword);
        Button btnCrearCuenta = findViewById(R.id.btnCrearCuenta);
        Button btnGoogle = findViewById(R.id.btnGoogle);
        TextView tvIniciarSesion = findViewById(R.id.tvIniciarSesion);

        btnCrearCuenta.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString();

            if (nombre.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            new Thread(() -> {
                Usuario existing = usuarioDao.getByEmail(email);
                if (existing != null) {
                    runOnUiThread(() -> Toast.makeText(this, "Email already registered", Toast.LENGTH_SHORT).show());
                } else {
                    Usuario user = new Usuario();
                    user.nombre = nombre;
                    user.email = email;
                    user.passwordHash = password; // In production, hash the password!
                    user.rol = "usuario";
                    usuarioDao.insert(user);
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    });
                }
            }).start();
        });

        btnGoogle.setOnClickListener(v -> {
            // Google authentication logic here
        });

        tvIniciarSesion.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }
}
