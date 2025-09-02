package com.example.baseapp.UI;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.baseapp.R;
import com.example.baseapp.adapter.ProfileAdapter;
import com.example.baseapp.model.ProfileItem;
import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends BaseUIActivity {

    private RecyclerView profileList;
    private ProfileAdapter adapter;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Configurar navegação (menu hamburger e barras)
        setupNavigation();

        // Configurar SharedPreferences
        prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);

        // Configurar RecyclerView
        profileList = findViewById(R.id.profile_list);
        profileList.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar lista de itens do perfil
        List<ProfileItem> profileItems = new ArrayList<>();
        profileItems.add(new ProfileItem("Nome", prefs.getString("user_name", "Usuário Anônimo"), false));
        profileItems.add(new ProfileItem("E-mail", prefs.getString("user_email", "email@exemplo.com"), false));
        profileItems.add(new ProfileItem("Editar Perfil", "", true));
        profileItems.add(new ProfileItem("Sair", "", true));

        adapter = new ProfileAdapter(this, profileItems, (item, position) -> {
            if ("Editar Perfil".equals(item.getTitle())) {
                showEditProfileDialog();
            } else if ("Sair".equals(item.getTitle())) {
                showLogoutDialog();
            }
        });
        profileList.setAdapter(adapter);
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void onFabClicked() {
        Toast.makeText(this, "FAB clicado na ProfileActivity", Toast.LENGTH_SHORT).show();
    }

    private void showEditProfileDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Editar Perfil");

        // Layout para o diálogo
        android.widget.LinearLayout layout = new android.widget.LinearLayout(this);
        layout.setOrientation(android.widget.LinearLayout.VERTICAL);
        layout.setPadding(32, 16, 32, 16);

        // Campo para nome
        final android.widget.EditText nameInput = new android.widget.EditText(this);
        nameInput.setHint("Nome");
        nameInput.setText(prefs.getString("user_name", "Usuário Anônimo"));
        layout.addView(nameInput);

        // Campo para e-mail
        final android.widget.EditText emailInput = new android.widget.EditText(this);
        emailInput.setHint("E-mail");
        emailInput.setText(prefs.getString("user_email", "email@exemplo.com"));
        layout.addView(emailInput);

        builder.setView(layout);

        builder.setPositiveButton("Salvar", (dialog, which) -> {
            String newName = nameInput.getText().toString().trim();
            String newEmail = emailInput.getText().toString().trim();
            if (!newName.isEmpty() && !newEmail.isEmpty()) {
                prefs.edit()
                        .putString("user_name", newName)
                        .putString("user_email", newEmail)
                        .apply();
                updateProfileItems();
                Toast.makeText(this, "Perfil atualizado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    private void showLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sair");
        builder.setMessage("Deseja sair do aplicativo?");
        builder.setPositiveButton("Sair", (dialog, which) -> {
            Toast.makeText(this, "Saindo do aplicativo", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    private void updateProfileItems() {
        List<ProfileItem> updatedItems = new ArrayList<>();
        updatedItems.add(new ProfileItem("Nome", prefs.getString("user_name", "Usuário Anônimo"), false));
        updatedItems.add(new ProfileItem("E-mail", prefs.getString("user_email", "email@exemplo.com"), false));
        updatedItems.add(new ProfileItem("Editar Perfil", "", true));
        updatedItems.add(new ProfileItem("Sair", "", true));
        adapter.updateItems(updatedItems);
    }
}