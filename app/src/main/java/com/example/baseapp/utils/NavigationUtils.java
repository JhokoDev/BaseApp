package com.example.baseapp.utils;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.example.baseapp.R;
import com.example.baseapp.UI.HomeActivity;
import com.example.baseapp.UI.SettingsActivity;
import com.google.android.material.navigation.NavigationView;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;

public class NavigationUtils {

    public static void setupNavigationDrawer(AppCompatActivity activity, Toolbar toolbar, DrawerLayout drawerLayout, NavigationView navigationView) {
        // Configurar a Toolbar como ActionBar
        activity.setSupportActionBar(toolbar);

        // Configurar o menu hamburger com ActionBarDrawerToggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                activity,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Configurar navegação do NavigationView
        navigationView.setNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                if (!(activity.getClass().getSimpleName().equals("HomeActivity"))) {
                    Intent intent = new Intent(activity, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    activity.startActivity(intent);
                    activity.finish();
                }
            } else if (itemId == R.id.nav_settings) {
                if (!(activity.getClass().getSimpleName().equals("SettingsActivity"))) {
                    Intent intent = new Intent(activity, SettingsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    activity.startActivity(intent);
                    activity.finish();
                }
            }
            drawerLayout.closeDrawers();
            return true;
        });

        // Configurar barras transparentes e edge-to-edge
        setupEdgeToEdge(activity);
    }

    private static void setupEdgeToEdge(AppCompatActivity activity) {
        Window window = activity.getWindow();
        WindowCompat.setDecorFitsSystemWindows(window, false);

        // Configurar barras transparentes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setNavigationBarColor(activity.getResources().getColor(android.R.color.transparent));
        }

        // Ajustar visibilidade dos ícones da barra de status
        WindowInsetsControllerCompat controller = WindowCompat.getInsetsController(window, window.getDecorView());
        if (controller != null) {
            controller.setAppearanceLightStatusBars(activity.getTheme().obtainStyledAttributes(
                    new int[]{android.R.attr.windowLightStatusBar}).getBoolean(0, false));
        }

        // Garantir que o conteúdo não seja oculto pelas barras
        View contentView = activity.findViewById(android.R.id.content);
        if (contentView != null) {
            contentView.setOnApplyWindowInsetsListener((view, insets) -> {
                WindowInsetsCompat insetsCompat = WindowInsetsCompat.toWindowInsetsCompat(insets);
                view.setPadding(
                        insetsCompat.getInsets(WindowInsetsCompat.Type.systemBars()).left,
                        insetsCompat.getInsets(WindowInsetsCompat.Type.systemBars()).top,
                        insetsCompat.getInsets(WindowInsetsCompat.Type.systemBars()).right,
                        insetsCompat.getInsets(WindowInsetsCompat.Type.systemBars()).bottom
                );
                return insets;
            });
        }
    }
}