# Solução de Problemas no BaseApp

## Introdução

Este documento fornece soluções para problemas comuns encontrados durante o desenvolvimento, configuração ou execução do **BaseApp**. Ele aborda erros de compilação, problemas com funcionalidades específicas (como imagem de perfil, permissões e notificações) e questões relacionadas a recursos ausentes, ajudando desenvolvedores a otimizar o processo de desenvolvimento.

## Visão Geral

O BaseApp é projetado para ser robusto, mas problemas podem surgir devido a configurações incorretas, dependências ausentes ou incompatibilidades com APIs Android. Este documento lista cenários comuns, suas causas prováveis e soluções práticas, com referências a outros documentos da pasta `/docs` para detalhes adicionais.

Os arquivos principais relacionados aos problemas abordados incluem:
- `app/build.gradle.kts`: Configuração de dependências.
- `app/src/main/java/com/example/baseapp/ui/ProfileActivity.java`: Lógica de perfil e imagem.
- `app/src/main/java/com/example/baseapp/utils/PermissionUtils.java`: Gerenciamento de permissões.
- `app/src/main/res/drawable/`: Recursos visuais, como ícones.

## Problemas Comuns e Soluções

### Erro de Compilação
- **Problema**: O projeto não compila, exibindo erros como "Cannot resolve symbol" ou falhas no Gradle.
- **Causa**: Dependências ausentes, configurações incorretas no `build.gradle.kts` ou incompatibilidade com o Android Gradle Plugin (AGP).
- **Solução**:
  1. Execute o comando de build com mais detalhes para identificar o erro:
     ```bash
     ./gradlew build --stacktrace --debug > build_log.txt
     ```
     Analise o arquivo `build_log.txt` para encontrar a causa.
  2. Verifique as dependências em `app/build.gradle.kts`:
     ```kotlin
     dependencies {
         implementation("androidx.core:core-ktx:1.13.1")
         implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.4")
         implementation("com.google.android.material:material:1.12.0")
         implementation("androidx.recyclerview:recyclerview:1.3.2")
         implementation("androidx.appcompat:appcompat:1.7.0")
         implementation("com.github.bumptech.glide:glide:4.16.0")
     }
     ```
  3. Adicione `android.suppressUnsupportedCompileSdk=34` ao `gradle.properties` se houver avisos de compatibilidade com o SDK 34.
  4. Sincronize o projeto no Android Studio: **File > Sync Project with Gradle Files**.

### Imagem de Perfil Não Circular
- **Problema**: A imagem de perfil na `ProfileActivity` não é exibida como circular.
- **Causa**: Problema com a biblioteca Glide ou configuração incorreta do `CircleCrop`.
- **Solução**:
  1. Confirme que a dependência do Glide está incluída em `app/build.gradle.kts`:
     ```kotlin
     implementation("com.github.bumptech.glide:glide:4.16.0")
     ```
  2. Verifique o código em `ProfileActivity.java` para garantir o uso de `CircleCrop`:
     ```java
     import com.bumptech.glide.Glide;
     import com.bumptech.glide.load.resource.bitmap.CircleCrop;

     Glide.with(this)
         .load(getProfileImagePathFromSharedPreferences())
         .transform(new CircleCrop())
         .placeholder(R.drawable.ic_profile)
         .into(profileImageView);
     ```
  3. Certifique-se de que o recurso `ic_profile.xml` existe em `app/src/main/res/drawable/`.

### Permissões Não Funcionam
- **Problema**: As permissões para notificações (`POST_NOTIFICATIONS`) ou acesso à galeria (`READ_MEDIA_IMAGES` ou `READ_EXTERNAL_STORAGE`) não são solicitadas corretamente.
- **Causa**: Configuração incorreta no `AndroidManifest.xml` ou lógica ausente em `PermissionUtils.java`.
- **Solução**:
  1. Verifique as permissões declaradas em `app/src/main/AndroidManifest.xml`:
     ```xml
     <uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
     <uses-permission android:name="android.permission.READ_MEDIA_IMAGES" android:maxSdkVersion="33" />
     <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" android:maxSdkVersion="32" />
     ```
  2. Confirme que `PermissionUtils.java` está implementando verificações e solicitações:
     ```java
     public static void requestPermission(Activity activity, String permission, int requestCode) {
         ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
     }
     ```
  3. Teste os diálogos de permissão em um dispositivo ou emulador com API 33+.
  4. Consulte [**Permissões Dinâmicas**](/docs/permissions.md) para mais detalhes.

### Recursos Ausentes
- **Problema**: Ícones ou outros recursos (ex.: `ic_home`, `ic_settings`, `ic_item1`, `ic_item2`, `ic_profile`) não são encontrados.
- **Causa**: Arquivos de recursos ausentes ou caminhos incorretos.
- **Solução**:
  1. Verifique se os arquivos de ícones estão em `app/src/main/res/drawable/`:
     - `ic_home.xml`
     - `ic_settings.xml`
     - `ic_item1.xml`
     - `ic_item2.xml`
     - `ic_profile.xml`
  2. Confirme que os ícones estão referenciados corretamente em layouts ou menus (ex.: `drawer_menu.xml`, `navigation_menu.xml`).
  3. Recompile o projeto para garantir que os recursos sejam gerados:
     ```bash
     ./gradlew cleanBuildCache
     ./gradlew build
     ```

### Notificações Não Aparecem
- **Problema**: Notificações de teste não são exibidas ao ativar o recurso em `SettingsActivity`.
- **Causa**: Falta de permissão `POST_NOTIFICATIONS` ou configuração incorreta do canal de notificação.
- **Solução**:
  1. Verifique a configuração do canal em `NotificationUtils.java`:
     ```java
     if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
         NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
         NotificationManager manager = context.getSystemService(NotificationManager.class);
         manager.createNotificationChannel(channel);
     }
     ```
  2. Consulte [**Notificações**](/docs/notifications.md) para mais detalhes sobre a configuração.

## Dicas Gerais para Depuração

- **Logs Detalhados**:
  - Use `Log.d` ou `Log.e` em classes como `ProfileActivity.java` ou `SettingsActivity.java` para rastrear erros:
    ```java
    Log.d("BaseApp", "Mensagem de depuração: " + variable);
    ```

- **Testes em Diferentes APIs**:
  - Teste o aplicativo em emuladores com APIs 23, 30 e 33+ para garantir compatibilidade.

- **Ferramentas do Android Studio**:
  - Use o **Logcat** para monitorar erros em tempo real.
  - Execute o **Lint** para identificar problemas no código e nos recursos.

## Próximos Passos

Para mais detalhes sobre os recursos relacionados, consulte:
- [**Customização**](/docs/customization.md) para ajustar funcionalidades específicas.
- [**Estrutura do Projeto**](/docs/project-structure.md) para entender a organização dos arquivos.
- [**Permissões Dinâmicas**](/docs/permissions.md) para resolver problemas com permissões.
- [**Notificações**](/docs/notifications.md) para configurar notificações corretamente.