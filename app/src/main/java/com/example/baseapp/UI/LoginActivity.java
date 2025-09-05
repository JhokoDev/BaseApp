package com.example.baseapp.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.credentials.Credential;
import androidx.credentials.CredentialManager;
import androidx.credentials.CustomCredential;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.exceptions.GetCredentialException;

import com.example.baseapp.R;
import com.google.android.libraries.identity.googleid.GetGoogleIdOption;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {

    private CredentialManager credentialManager;
    private SharedPreferences prefs;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        credentialManager = CredentialManager.create(this);
        prefs = getSharedPreferences("BaseAppPrefs", MODE_PRIVATE);

        Button googleSignInButton = findViewById(R.id.button_google_sign_in);
        googleSignInButton.setOnClickListener(v -> signInWithGoogle());

        // Verificar se já está logado
        if (prefs.getString("google_id_token", null) != null) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }
    }

    private void signInWithGoogle() {
        GetGoogleIdOption googleIdOption = new GetGoogleIdOption.Builder()
                .setServerClientId(getString(R.string.default_web_client_id))
                .setFilterByAuthorizedAccounts(false)
                .setAutoSelectEnabled(true)
                .build();

        GetCredentialRequest request = new GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build();

        Executor executor = Executors.newSingleThreadExecutor();

        credentialManager.getCredentialAsync(
                this,
                request,
                null, // CancellationSignal
                executor,
                new androidx.credentials.CredentialManagerCallback<GetCredentialResponse, GetCredentialException>() {
                    @Override
                    public void onResult(GetCredentialResponse response) {
                        handleSignIn(response);
                    }

                    @Override
                    public void onError(GetCredentialException e) {
                        Log.e(TAG, "Erro ao autenticar com Google: " + e.getMessage());
                        runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Falha no login: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    }
                }
        );
    }

    private void handleSignIn(GetCredentialResponse response) {
        Credential credential = response.getCredential();
        if (credential instanceof CustomCredential && credential.getType().equals(GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL)) {
            GoogleIdTokenCredential googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.getData());
            String idToken = googleIdTokenCredential.getIdToken();
            String displayName = googleIdTokenCredential.getDisplayName() != null ? googleIdTokenCredential.getDisplayName() : "Usuário Anônimo";

            // Salvar dados no SharedPreferences
            prefs.edit()
                    .putString("google_id_token", idToken)
                    .putString("user_name", displayName)
                    .apply();

            // Executar Toast e navegação na main thread
            runOnUiThread(() -> {
                Toast.makeText(LoginActivity.this, "Bem-vindo, " + displayName + "!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();
            });
        } else {
            runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Credencial inválida", Toast.LENGTH_SHORT).show());
        }
    }
}
