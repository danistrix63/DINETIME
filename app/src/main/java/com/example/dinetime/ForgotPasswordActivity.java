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

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText emailEditText;
    Button resetPasswordButton;
    TextView loginText;
    private UsuarioDao usuarioDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        usuarioDao = AppDatabase.getInstance(this).usuarioDao();

        emailEditText = findViewById(R.id.emailEditText);
        resetPasswordButton = findViewById(R.id.resetPasswordButton);
        loginText = findViewById(R.id.loginText);

        resetPasswordButton.setOnClickListener(view -> {
            String email = emailEditText.getText().toString().trim();

            if (email.isEmpty()) {
                Toast.makeText(ForgotPasswordActivity.this, "Introduce un correo válido", Toast.LENGTH_SHORT).show();
                return;
            }

            new Thread(() -> {
                Usuario user = usuarioDao.getByEmail(email);
                runOnUiThread(() -> {
                    if (user != null) {
                        Toast.makeText(ForgotPasswordActivity.this, "Enlace de recuperación enviado a " + email, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ForgotPasswordActivity.this, "No se encontró el correo", Toast.LENGTH_SHORT).show();
                    }
                });
            }).start();
        });

        loginText.setOnClickListener(view -> {
            startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
            finish();
        });
    }
}
