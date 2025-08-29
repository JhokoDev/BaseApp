package com.example.baseapp.UI;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import com.example.baseapp.R;
import com.example.baseapp.utils.NavigationUtils;
import com.google.android.material.navigation.NavigationView;
import androidx.appcompat.widget.Toolbar;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.toolbar);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_drawer);

        // Configurar menu hamburger e barras
        NavigationUtils.setupNavigationDrawer(this, toolbar, drawerLayout, navigationView, R.id.nav_graph);
    }
}