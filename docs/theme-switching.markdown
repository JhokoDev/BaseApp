# Tema Dinâmico no BaseApp

## Introdução

Este documento descreve como implementar e personalizar a funcionalidade de alternância de temas (claro/escuro) no **BaseApp**. A alternância de temas é gerenciada na `SettingsActivity`, permitindo que o usuário escolha entre os modos claro e escuro, com a preferência sendo persistida usando `SharedPreferences`. A implementação utiliza os temas do **Material Design 3**, garantindo uma experiência visual consistente e moderna.

## Visão Geral

O BaseApp suporta a alternância dinâmica entre os modos claro e escuro, ajustando automaticamente a interface do usuário (UI) com base na escolha do usuário. A funcionalidade é implementada com a biblioteca `AppCompat` e temas do Material Design 3, garantindo compatibilidade com diferentes versões do Android.

Os arquivos principais relacionados estão localizados em:
- `app/src/main/java/com/example/baseapp/ui/SettingsActivity.java`: Lógica para alternar e persistir o tema.
- `app/src/main/res/values/themes.xml`: Definição do tema claro.
- `app/src/main/res/values/themes-night.xml`: Definição do tema escuro.
- `app/src/main/res/layout/activity_settings.xml`: Layout com o `Switch` para alternância de tema.

## Configurando a Alternância de Tema

1. **Definir Temas Claro e Escuro**:
   - No arquivo `app/src/main/res/values/themes.xml`, configure o tema claro:
     ```xml
     <style name="AppTheme" parent="Theme.Material3.DayNight.NoActionBar">
         <item name="colorPrimary">@color/md_theme_light_primary</item>
         <item name="colorSecondary">@color/md_theme_light_secondary</item>
         <item name="android:statusBarColor">@android:color/transparent</item>
         <item name="android:windowLightStatusBar">true</item>
     </style>
     ```
   - No arquivo `app/src/main/res/values/themes-night.xml`, configure o tema escuro:
     ```xml
     <style name="AppTheme" parent="Theme.Material3.DayNight.NoActionBar">
         <item name="colorPrimary">@color/md_theme_dark_primary</item>
         <item name="colorSecondary">@color/md_theme_dark_secondary</item>
         <item name="android:statusBarColor">@android:color/transparent</item>
         <item name="android:windowLightStatusBar">false</item>
     </style>
     ```

2. **Implementar a Alternância de Tema**:
   - Em `SettingsActivity.java`, use um `Switch` no `RecyclerView` para alternar entre os temas:
     ```java
     import androidx.appcompat.app.AppCompatDelegate;

     public void toggleTheme(boolean isDarkMode) {
         SharedPreferences prefs = getSharedPreferences("BaseAppPrefs", MODE_PRIVATE);
         prefs.edit().putBoolean("dark_mode_enabled", isDarkMode).apply();
         if (isDarkMode) {
             AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
         } else {
             AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
         }
     }
     ```

3. **Carregar o Tema Salvo**:
   - Em `BaseUIActivity.java`, carregue a preferência de tema ao iniciar a atividade:
     ```java
     @Override
     protected void onCreate(Bundle savedInstanceState) {
         SharedPreferences prefs = getSharedPreferences("BaseAppPrefs", MODE_PRIVATE);
         boolean isDarkMode = prefs.getBoolean("dark_mode_enabled", false);
         AppCompatDelegate.setDefaultNightMode(
             isDarkMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
         );
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_base);
     }
     ```

## Personalização

- **Ajustar Cores do Tema**:
  - Modifique as cores em `app/src/main/res/values/colors.xml` para personalizar os temas claro e escuro:
    ```xml
    <color name="md_theme_light_primary">#3F51B5</color>
    <color name="md_theme_dark_primary">#7986CB</color>
    ```

- **Adicionar Temas Personalizados**:
  - Crie novos estilos em `themes.xml` ou `themes-night.xml` para temas adicionais (ex.: tema de alto contraste).
  - Atualize a lógica em `SettingsActivity.java` para suportar múltiplas opções de tema.

- **Sincronizar com o Sistema**:
  - Para seguir o tema do sistema, use:
    ```java
    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    ```

## Solução de Problemas

- **Tema Não Alterna**:
  - Verifique se `AppCompatDelegate.setDefaultNightMode` está sendo chamado corretamente.
  - Confirme que os arquivos `themes.xml` e `themes-night.xml` estão configurados com o mesmo nome de estilo (`AppTheme`).

- **Cores Inconsistentes**:
  - Certifique-se de que as cores definidas em `colors.xml` são usadas consistentemente em ambos os temas.
  - Verifique se `android:windowLightStatusBar` está ajustado para o fundo do tema.

- **Tema Não Persiste**:
  - Confirme que `SharedPreferences` está salvando o estado em `toggleTheme` e sendo lido em `onCreate`.

## Próximos Passos

Para integrar a alternância de tema com outros recursos, consulte:
- [**Barras Transparentes (Edge-to-Edge)**](/docs/edge-to-edge.md) para sincronizar as barras de status e navegação com o tema.
- [**Gestão de Perfil**](/docs/profile-management.md) para ajustar a UI do perfil ao tema selecionado.