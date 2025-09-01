package com.example.baseapp.UI;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import com.example.baseapp.R;
import com.example.baseapp.utils.NavigationUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public abstract class BaseUIActivity extends AppCompatActivity {

    // Declaração da variável fab para corrigir o erro
    protected FloatingActionButton fab;
    protected BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // O layout será definido pelas subclasses (HomeActivity ou SettingsActivity)
    }

    protected void setupNavigation() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_drawer);

        // Configurar menu hamburger e barras transparentes
        NavigationUtils.setupNavigationDrawer(this, toolbar, drawerLayout, navigationView);

        // Configurar FloatingActionButton
        fab = findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(view -> onFabClicked());
        }

        // Configurar BottomNavigationView (vitrine, sem funcionalidade por enquanto)
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        if (bottomNavigationView != null) {
            // Sem listener por enquanto, apenas vitrine
        }
    }

    protected abstract int getLayoutId();

    // Método abstrato para ser implementado pelas subclasses
    protected abstract void onFabClicked();
}