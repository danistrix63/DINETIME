package com.example.dinetime;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class BottomNavUtil {
    public static void setup(final Activity activity, int selectedNavId) {
        View navHome = activity.findViewById(R.id.nav_home);
        View navReservations = activity.findViewById(R.id.nav_reservations);
        View navProfile = activity.findViewById(R.id.nav_profile);

        if (navHome != null) {
            navHome.setSelected(selectedNavId == R.id.nav_home);
            navHome.setOnClickListener(v -> {
                if (!(activity instanceof HomeActivity)) {
                    activity.startActivity(new Intent(activity, HomeActivity.class));
                }
            });
        }
        if (navReservations != null) {
            navReservations.setSelected(selectedNavId == R.id.nav_reservations);
            navReservations.setOnClickListener(v -> {
                if (!(activity instanceof BookingActivity)) {
                    activity.startActivity(new Intent(activity, BookingActivity.class));
                }
            });
        }
        if (navProfile != null) {
            navProfile.setSelected(selectedNavId == R.id.nav_profile);
            navProfile.setOnClickListener(v -> {
                if (!(activity instanceof ProfileActivity)) {
                    activity.startActivity(new Intent(activity, ProfileActivity.class));
                }
            });
        }
    }
}
