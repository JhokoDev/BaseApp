package com.example.baseapp.UI;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.baseapp.R;
import com.example.baseapp.adapter.SettingsAdapter;
import com.example.baseapp.model.SettingItem;
import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends BaseUIActivity {

    private RecyclerView settingsList;
    private SettingsAdapter adapter;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Configurar navegação (menu hamburger e barras)
        setupNavigation();

        // Configurar RecyclerView
        settingsList = findViewById(R.id.settings_list);
        settingsList.setLayoutManager(new LinearLayoutManager(this));
        prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);

        // Inicializar lista de configurações
        List<SettingItem> settingItems = new ArrayList<>();
        settingItems.add(new SettingItem(
                getString(R.string.settings_notifications),
                getString(R.string.settings_notifications_desc),
                "notifications_enabled",
                true,
                prefs.getBoolean("notifications_enabled", true)
        ));
        settingItems.add(new SettingItem(
                getString(R.string.settings_theme),
                getString(R.string.settings_theme_desc),
                "theme_selection",
                false,
                false
        ));

        adapter = new SettingsAdapter(this, settingItems);
        settingsList.setAdapter(adapter);
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