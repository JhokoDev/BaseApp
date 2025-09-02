package com.example.baseapp.UI;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.baseapp.R;
import com.example.baseapp.adapter.ProfileAdapter;
import com.example.baseapp.model.ProfileItem;
import com.example.baseapp.utils.PermissionUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends BaseUIActivity {

    private RecyclerView profileList;
    private ProfileAdapter adapter;
    private SharedPreferences prefs;
    private ImageView profileImage;
    private ActivityResultLauncher<Intent> imagePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Configurar navegação
        setupNavigation();

        // Configurar SharedPreferences
        prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);

        // Configurar ImageView
        profileImage = findViewById(R.id.profile_image);
        String imagePath = prefs.getString("profile_image_path", "");
        if (!imagePath.isEmpty()) {
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                profileImage.setImageBitmap(bitmap);
            }
        }

        // Configurar RecyclerView
        profileList = findViewById(R.id.profile_list);
        profileList.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar lista de itens do perfil
        List<ProfileItem> profileItems = new ArrayList<>();
        profileItems.add(new ProfileItem(getString(R.string.profile_name), prefs.getString("user_name", "Usuário Anônimo"), false));
        profileItems.add(new ProfileItem(getString(R.string.profile_email), prefs.getString("user_email", "email@exemplo.com"), false));
        profileItems.add(new ProfileItem(getString(R.string.profile_change_image), "", true));
        profileItems.add(new ProfileItem(getString(R.string.profile_edit), "", true));
        profileItems.add(new ProfileItem(getString(R.string.profile_logout), "", true));

        adapter = new ProfileAdapter(this, profileItems, (item, position) -> {
            if (getString(R.string.profile_change_image).equals(item.getTitle())) {
                if (PermissionUtils.isStoragePermissionGranted(this)) {
                    pickImage();
                } else {
                    PermissionUtils.requestStoragePermission(this);
                }
            } else if (getString(R.string.profile_edit).equals(item.getTitle())) {
                showEditProfileDialog();
            } else if (getString(R.string.profile_logout).equals(item.getTitle())) {
                showLogoutDialog();
            }
        });
        profileList.setAdapter(adapter);

        // Configurar lançador para selecionar imagem
        imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Uri imageUri = result.getData().getData();
                if (imageUri != null) {
                    try {
                        // Copiar imagem para armazenamento interno
                        String newImagePath = saveImageToInternalStorage(imageUri);
                        if (newImagePath != null) {
                            Bitmap bitmap = BitmapFactory.decodeFile(newImagePath);
                            profileImage.setImageBitmap(bitmap);
                            prefs.edit().putString("profile_image_path", newImagePath).apply();
                            Toast.makeText(this, "Imagem de perfil atualizada", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Falha ao salvar a imagem", Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        Toast.makeText(this, "Erro ao processar a imagem", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void onFabClicked() {
        Toast.makeText(this, "FAB clicado na ProfileActivity", Toast.LENGTH_SHORT).show();
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imagePickerLauncher.launch(intent);
    }

    private String saveImageToInternalStorage(Uri imageUri) throws IOException {
        // Criar diretório para salvar a imagem
        File directory = new File(getFilesDir(), "profile_images");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Criar arquivo para a imagem
        File imageFile = new File(directory, "profile_image_" + System.currentTimeMillis() + ".jpg");

        // Copiar imagem do URI para o arquivo
        try (InputStream inputStream = getContentResolver().openInputStream(imageUri);
             FileOutputStream outputStream = new FileOutputStream(imageFile)) {
            if (inputStream != null) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                return imageFile.getAbsolutePath();
            }
        }
        return null;
    }

    private void showEditProfileDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.profile_edit));

        android.widget.LinearLayout layout = new android.widget.LinearLayout(this);
        layout.setOrientation(android.widget.LinearLayout.VERTICAL);
        layout.setPadding(32, 16, 32, 16);

        final android.widget.EditText nameInput = new android.widget.EditText(this);
        nameInput.setHint(getString(R.string.profile_name));
        nameInput.setText(prefs.getString("user_name", "Usuário Anônimo"));
        layout.addView(nameInput);

        final android.widget.EditText emailInput = new android.widget.EditText(this);
        emailInput.setHint(getString(R.string.profile_email));
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
        builder.setTitle(getString(R.string.profile_logout));
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
        updatedItems.add(new ProfileItem(getString(R.string.profile_name), prefs.getString("user_name", "Usuário Anônimo"), false));
        updatedItems.add(new ProfileItem(getString(R.string.profile_email), prefs.getString("user_email", "email@exemplo.com"), false));
        updatedItems.add(new ProfileItem(getString(R.string.profile_change_image), "", true));
        updatedItems.add(new ProfileItem(getString(R.string.profile_edit), "", true));
        updatedItems.add(new ProfileItem(getString(R.string.profile_logout), "", true));
        adapter.updateItems(updatedItems);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionUtils.STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickImage();
                Toast.makeText(this, "Permissão de armazenamento concedida", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permissão de armazenamento negada", Toast.LENGTH_SHORT).show();
            }
        }
    }
}