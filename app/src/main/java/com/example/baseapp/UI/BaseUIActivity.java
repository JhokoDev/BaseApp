package com.example.baseapp.UI;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.baseapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public abstract class BaseUIActivity extends AppCompatActivity {

    protected DrawerLayout drawerLayout;
    protected NavigationView navDrawer;
    protected Toolbar toolbar;
    protected NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        setupUI();
    }

    protected abstract int getLayoutId();

    protected void setupUI() {
        drawerLayout = findViewById(R.id.drawer_layout);
        navDrawer = findViewById(R.id.nav_drawer);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_content_main);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph())
                    .setOpenableLayout(drawerLayout)
                    .build();
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
            NavigationUI.setupWithNavController(navDrawer, navController);
        }

        // Configurar BottomNavigationView para navegação entre fragments
        BottomNavigationView bottomNav = findViewById(R.id.navigation);
        if (bottomNav != null && navController != null) {
            NavigationUI.setupWithNavController(bottomNav, navController);
        }

        // Configurar FloatingActionButton
        FloatingActionButton fab = findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(view -> {
                Toast.makeText(this, "FAB clicked!", Toast.LENGTH_SHORT).show();
            });
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (navController != null) {
            return NavigationUI.navigateUp(navController, drawerLayout) || super.onSupportNavigateUp();
        }
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}