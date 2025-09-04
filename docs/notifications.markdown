# Notificações no BaseApp

## Introdução

Este documento descreve como configurar e disparar notificações no **BaseApp**, incluindo a implementação de notificações de teste com ícones personalizados e a integração com permissões dinâmicas (`POST_NOTIFICATIONS`). As notificações são gerenciadas na `SettingsActivity`, permitindo que o usuário ative/desative notificações e visualize notificações de teste.

## Visão Geral

O BaseApp utiliza a API de notificações do Android para criar e exibir notificações com ícones personalizados. A funcionalidade inclui a solicitação de permissão `POST_NOTIFICATIONS` (necessária a partir da API 33) e a configuração de um canal de notificação para compatibilidade com Android 8.0+ (API 26+).

Os arquivos principais relacionados estão localizados em:
- `app/src/main/java/com/example/baseapp/ui/SettingsActivity.java`: Lógica para ativar/desativar notificações e disparar notificações de teste.
- `app/src/main/java/com/example/baseapp/utils/NotificationUtils.java`: Utilitários para criar e gerenciar notificações.
- `app/src/main/res/drawable/ic_notification.xml`: Ícone personalizado para notificações.
- `app/src/main/AndroidManifest.xml`: Declaração da permissão `POST_NOTIFICATIONS`.

## Configurando Notificações

1. **Declarar Permissão no Manifest**:
   - No arquivo `app/src/main/AndroidManifest.xml`, adicione a permissão necessária:
     ```xml
     <uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
     ```

2. **Criar um Canal de Notificação**:
   - Em `NotificationUtils.java`, configure um canal de notificação para Android 8.0+:
     ```java
     import android.app.NotificationChannel;
     import android.app.NotificationManager;
     import android.content.Context;

     public class NotificationUtils {
         public static final String CHANNEL_ID = "baseapp_channel";
         public static final String CHANNEL_NAME = "BaseApp Notifications";

         public static void createNotificationChannel(Context context) {
             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                 NotificationChannel channel = new NotificationChannel(
                     CHANNEL_ID,
                     CHANNEL_NAME,
                     NotificationManager.IMPORTANCE_DEFAULT
                 );
                 channel.setDescription("Canal para notificações do BaseApp");
                 NotificationManager manager = context.getSystemService(NotificationManager.class);
                 manager.createNotificationChannel(channel);
             }
         }
     }
     ```

3. **Disparar uma Notificação de Teste**:
   - Em `SettingsActivity.java`, implemente a lógica para disparar uma notificação após verificar a permissão:
     ```java
     import androidx.core.app.NotificationCompat;
     import androidx.core.app.NotificationManagerCompat;

     public void sendTestNotification() {
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !PermissionUtils.hasPermission(this, Manifest.permission.POST_NOTIFICATIONS)) {
             requestNotificationPermission();
             return;
         }

         NotificationUtils.createNotificationChannel(this);
         NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NotificationUtils.CHANNEL_ID)
             .setSmallIcon(R.drawable.ic_notification)
             .setContentTitle("Notificação de Teste")
             .setContentText("Esta é uma notificação de teste do BaseApp.")
             .setPriority(NotificationCompat.PRIORITY_DEFAULT)
             .setAutoCancel(true);

         NotificationManagerCompat manager = NotificationManagerCompat.from(this);
         manager.notify(1, builder.build());
     }
     ```

4. **Ativar/Desativar Notificações**:
   - Use um `Switch` no `RecyclerView` de `SettingsActivity` para gerenciar o estado das notificações:
     ```java
     public void toggleNotifications(boolean isEnabled) {
         SharedPreferences prefs = getSharedPreferences("BaseAppPrefs", MODE_PRIVATE);
         prefs.edit().putBoolean("notifications_enabled", isEnabled).apply();
         if (isEnabled) {
             sendTestNotification();
         }
     }
     ```

## Personalização

- **Customizar o Conteúdo da Notificação**:
  - Modifique o título, texto e ícone em `sendTestNotification`:
    ```java
    .setContentTitle("Bem-vindo ao BaseApp")
    .setContentText("Confira as novidades do aplicativo!")
    .setSmallIcon(R.drawable.ic_custom_notification)
    ```

- **Adicionar Ações**:
  - Inclua botões de ação na notificação:
    ```java
    Intent intent = new Intent(this, HomeActivity.class);
    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
    builder.addAction(R.drawable.ic_home, "Abrir Início", pendingIntent);
    ```

- **Estilizar Notificações**:
  - Use estilos como `BigTextStyle` para notificações mais detalhadas:
    ```java
    builder.setStyle(new NotificationCompat.BigTextStyle()
        .bigText("Esta é uma notificação de teste com texto expandido."));
    ```

## Solução de Problemas

- **Notificação Não Aparece**:
  - Verifique se a permissão `POST_NOTIFICATIONS` está declarada no `AndroidManifest.xml`.
  - Confirme que o canal de notificação foi criado chamando `NotificationUtils.createNotificationChannel`.

- **Ícone da Notificação Ausente**:
  - Certifique-se de que `ic_notification.xml` está em `app/src/main/res/drawable/`.
  - Use um ícone compatível (ex.: formato vetorial ou PNG com fundo transparente).

- **Permissão Negada**:
  - Consulte [**Permissões Dinâmicas**](/docs/permissions.md) para gerenciar a solicitação de `POST_NOTIFICATIONS`.

## Próximos Passos

Para integrar notificações com outros recursos, consulte:
- [**Permissões Dinâmicas**](/docs/permissions.md) para gerenciar a permissão `POST_NOTIFICATIONS`.
- [**Configurações**](/docs/settings.md) para ajustar a interface de ativação/desativação de notificações.