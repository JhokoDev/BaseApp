# Permissões Dinâmicas no BaseApp

## Introdução

Este documento descreve como gerenciar permissões dinâmicas no **BaseApp**, incluindo permissões para notificações (`POST_NOTIFICATIONS`) e acesso à galeria (`READ_MEDIA_IMAGES` para API 33+ ou `READ_EXTERNAL_STORAGE` para APIs anteriores). As permissões dinâmicas são implementadas seguindo as diretrizes do Android para garantir conformidade com APIs modernas e uma experiência de usuário segura e fluida.

## Visão Geral

O BaseApp utiliza permissões dinâmicas para acessar recursos sensíveis do dispositivo, como notificações e armazenamento de mídia. A implementação é feita por meio da API do AndroidX, com verificações de compatibilidade para diferentes versões do Android. As permissões são solicitadas apenas quando necessário, e diálogos do sistema são exibidos para obter consentimento do usuário.

Os arquivos principais relacionados estão localizados em:
- `app/src/main/java/com/example/baseapp/ui/ProfileActivity.java`: Solicitação de permissão para acesso à galeria.
- `app/src/main/java/com/example/baseapp/ui/SettingsActivity.java`: Solicitação de permissão para notificações.
- `app/src/main/java/com/example/baseapp/utils/PermissionUtils.java`: Utilitários para gerenciar permissões.
- `app/src/main/AndroidManifest.xml`: Declaração das permissões necessárias.

## Configurando Permissões Dinâmicas

1. **Declarar Permissões no Manifest**:
   - No arquivo `app/src/main/AndroidManifest.xml`, adicione as permissões necessárias:
     ```xml
     <uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
     <uses-permission android:name="android.permission.READ_MEDIA_IMAGES"
         android:maxSdkVersion="33" />
     <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"
         android:maxSdkVersion="32" />
     ```

2. **Verificar e Solicitar Permissões**:
   - Em `PermissionUtils.java`, implemente métodos para verificar e solicitar permissões:
     ```java
     import androidx.core.app.ActivityCompat;
     import androidx.core.content.ContextCompat;
     import android.content.Context;
     import android.content.pm.PackageManager;

     public class PermissionUtils {
         public static boolean hasPermission(Context context, String permission) {
             return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
         }

         public static void requestPermission(Activity activity, String permission, int requestCode) {
             ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
         }
     }
     ```

3. **Solicitar Permissão para Notificações**:
   - Em `SettingsActivity.java`, solicite a permissão `POST_NOTIFICATIONS` (API 33+):
     ```java
     private static final int NOTIFICATION_PERMISSION_CODE = 100;

     public void requestNotificationPermission() {
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
             if (!PermissionUtils.hasPermission(this, Manifest.permission.POST_NOTIFICATIONS)) {
                 PermissionUtils.requestPermission(this, Manifest.permission.POST_NOTIFICATIONS, NOTIFICATION_PERMISSION_CODE);
             }
         }
     }

     @Override
     public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
         super.onRequestPermissionsResult(requestCode, permissions, grantResults);
         if (requestCode == NOTIFICATION_PERMISSION_CODE) {
             if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                 Toast.makeText(this, "Permissão de notificações concedida", Toast.LENGTH_SHORT).show();
             } else {
                 Toast.makeText(this, "Permissão de notificações negada", Toast.LENGTH_SHORT).show();
             }
         }
     }
     ```

4. **Solicitar Permissão para Galeria**:
   - Em `ProfileActivity.java`, solicite a permissão apropriada para acesso à galeria:
     ```java
     private static final int STORAGE_PERMISSION_CODE = 101;

     public void requestStoragePermission() {
         String permission = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ?
             Manifest.permission.READ_MEDIA_IMAGES : Manifest.permission.READ_EXTERNAL_STORAGE;
         if (!PermissionUtils.hasPermission(this, permission)) {
             PermissionUtils.requestPermission(this, permission, STORAGE_PERMISSION_CODE);
         } else {
             openGallery();
         }
     }

     @Override
     public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
         super.onRequestPermissionsResult(requestCode, permissions, grantResults);
         if (requestCode == STORAGE_PERMISSION_CODE) {
             if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                 openGallery();
             } else {
                 Toast.makeText(this, "Permissão de armazenamento negada", Toast.LENGTH_SHORT).show();
             }
         }
     }
     ```

## Personalização

- **Mensagens de Diálogo**:
  - Antes de solicitar permissões, mostre um diálogo explicativo para informar o usuário sobre a necessidade da permissão:
    ```java
    public void showPermissionRationaleDialog(String permission, int requestCode) {
        new AlertDialog.Builder(this)
            .setTitle("Permissão Necessária")
            .setMessage("Esta permissão é necessária para acessar a galeria.")
            .setPositiveButton("OK", (dialog, which) -> PermissionUtils.requestPermission(this, permission, requestCode))
            .setNegativeButton("Cancelar", null)
            .show();
    }
    ```

- **Compatibilidade com APIs Antigas**:
  - Adicione verificações condicionais para APIs abaixo de 33, como mostrado no exemplo de `requestStoragePermission`.

- **Persistência de Estado**:
  - Armazene o estado das permissões em `SharedPreferences` para evitar solicitações repetitivas.

## Solução de Problemas

- **Permissão Não Solicitada**:
  - Verifique se as permissões estão declaradas corretamente no `AndroidManifest.xml`.
  - Confirme que `PermissionUtils.java` está sendo usado para verificar e solicitar permissões.

- **Diálogo de Permissão Não Aparece**:
  - Certifique-se de que `ActivityCompat.requestPermissions` está sendo chamado com os parâmetros corretos.
  - Verifique se o dispositivo está rodando uma API compatível (ex.: API 33+ para `POST_NOTIFICATIONS`).

- **Permissão Negada Permanentemente**:
  - Adicione lógica para redirecionar o usuário às configurações do aplicativo:
    ```java
    public void openAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }
    ```

## Próximos Passos

Para integrar permissões dinâmicas com outros recursos, consulte:
- [**Gestão de Perfil**](/docs/profile-management.md) para gerenciar permissões de acesso à galeria.
- [**Notificações**](/docs/notifications.md) para configurar notificações após a concessão de permissões.