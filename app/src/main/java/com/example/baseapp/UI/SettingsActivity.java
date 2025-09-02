package com.example.baseapp.UI;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.baseapp.R;
import com.example.baseapp.adapter.SettingsAdapter;
import com.example.baseapp.model.SettingItem;
import com.example.baseapp.utils.NotificationUtils;
import com.example.baseapp.utils.PermissionUtils;
import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends BaseUIActivity {

    private RecyclerView settingsList;
    private SettingsAdapter adapter;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Carregar o tema salvo antes de setContentView
        prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        boolean isDarkMode = prefs.getBoolean("theme_selection", false);
        AppCompatDelegate.setDefaultNightMode(isDarkMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Configurar navegação (menu hamburger e barras)
        setupNavigation();

        // Configurar RecyclerView
        settingsList = findViewById(R.id.settings_list);
        settingsList.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar lista de configurações
        List<SettingItem> settingItems = new ArrayList<>();
        settingItems.add(new SettingItem(
                getString(R.string.settings_notifications),
                getString(R.string.settings_notifications_desc),
                "notifications_enabled",
                true,
                prefs.getBoolean("notifications_enabled", false)
        ));
        settingItems.add(new SettingItem(
                getString(R.string.settings_theme),
                getString(R.string.settings_theme_desc),
                "theme_selection",
                true,
                isDarkMode
        ));

        adapter = new SettingsAdapter(this, settingItems, (item, isChecked) -> {
            if ("notifications_enabled".equals(item.getKey())) {
                if (isChecked) {
                    if (PermissionUtils.isNotificationPermissionGranted(this)) {
                        NotificationUtils.sendTestNotification(this);
                        prefs.edit().putBoolean(item.getKey(), true).apply();
                        Toast.makeText(this, "Notificações ativadas", Toast.LENGTH_SHORT).show();
                    } else {
                        PermissionUtils.requestNotificationPermission(this);
                        item.setToggleValue(false); // Reverter até a permissão ser concedida
                    }
                } else {
                    prefs.edit().putBoolean(item.getKey(), false).apply();
                    Toast.makeText(this, "Notificações desativadas", Toast.LENGTH_SHORT).show();
                }
            } else if ("theme_selection".equals(item.getKey())) {
                AppCompatDelegate.setDefaultNightMode(isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
                prefs.edit().putBoolean(item.getKey(), isChecked).apply();
                Toast.makeText(this, "Modo Escuro " + (isChecked ? "ativado" : "desativado"), Toast.LENGTH_SHORT).show();
            }
            adapter.notifyDataSetChanged();
        });
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionUtils.NOTIFICATION_PERMISSION_CODE) {
            for (SettingItem item : adapter.getSettingItems()) {
                if ("notifications_enabled".equals(item.getKey())) {
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        item.setToggleValue(true);
                        prefs.edit().putBoolean(item.getKey(), true).apply();
                        NotificationUtils.sendTestNotification(this);
                        Toast.makeText(this, "Permissão de notificações concedida", Toast.LENGTH_SHORT).show();
                    } else {
                        item.setToggleValue(false);
                        prefs.edit().putBoolean(item.getKey(), false).apply();
                        Toast.makeText(this, "Permissão de notificações negada", Toast.LENGTH_SHORT).show();
                    }
                    adapter.notifyDataSetChanged();
                    break;
                }
            }
        }
    }
}