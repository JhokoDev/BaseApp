package com.example.baseapp.UI;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import com.example.baseapp.R;
import com.example.baseapp.utils.NavigationUtils;
import com.google.android.material.navigation.NavigationView;
import androidx.appcompat.widget.Toolbar;

public class SettingsActivity extends BaseUIActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.toolbar);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_drawer);

        // Configurar menu hamburger e barras
        NavigationUtils.setupNavigationDrawer(this, toolbar, drawerLayout, navigationView);
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void onFabClicked() {
        Toast.makeText(this, "FAB clicado na SettingsActivity", Toast.LENGTH_SHORT).show();
    }
}