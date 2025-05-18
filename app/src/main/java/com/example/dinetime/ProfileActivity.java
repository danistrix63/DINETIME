package com.example.dinetime;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import java.util.Locale;

public class ProfileActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "settings";
    private static final String KEY_DARK_MODE = "dark_mode";
    private static final String KEY_LANGUAGE = "language";
    private static final String USER_PREFS = "user_session";
    private static final String USER_NAME = "user_name";
    private static final String USER_EMAIL = "user_email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_profile);
        // Setup bottom navigation (no tab selected)
        BottomNavUtil.setup(this, 0);
        // Get views

        TextView userEmail = findViewById(R.id.userEmail);
        Switch switchDarkMode = findViewById(R.id.switchDarkMode);
        Button btnLogout = findViewById(R.id.btnLogout);

        // Load user data from SharedPreferences (replace with your session logic if needed)
        SharedPreferences userPrefs = getSharedPreferences(USER_PREFS, MODE_PRIVATE);
        String name = userPrefs.getString(USER_NAME, "Nombre");
        String email = userPrefs.getString(USER_EMAIL, "Correo");

        userEmail.setText(email);

        // Dark mode switch
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean isDark = prefs.getBoolean(KEY_DARK_MODE, false);
        switchDarkMode.setChecked(isDark);

        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            AppCompatDelegate.setDefaultNightMode(
                isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
            );
            prefs.edit().putBoolean(KEY_DARK_MODE, isChecked).apply();
        });

        // Logout button
        btnLogout.setOnClickListener(v -> {
            // Clear user session (example)
            userPrefs.edit().clear().apply();
            // Redirect to login (replace with your login activity)
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Resources res = getResources();
        Configuration config = res.getConfiguration();
        config.setLocale(locale);
        res.updateConfiguration(config, res.getDisplayMetrics());
        getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
            .edit().putString(KEY_LANGUAGE, lang).apply();
    }

    private void loadLocale() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String lang = prefs.getString(KEY_LANGUAGE, Locale.getDefault().getLanguage());
        setLocale(lang);
    }
}
