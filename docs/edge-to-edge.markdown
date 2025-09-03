[profile-management.markdown](..%2F..%2F..%2F..%2FDownloads%2Fprofile-management.markdown)# Barras Transparentes (Edge-to-Edge) no BaseApp

## Introdução

Este documento descreve como implementar e personalizar barras transparentes (edge-to-edge) no **BaseApp**, permitindo que o conteúdo da aplicação se estenda sob as barras de status e navegação do Android. Essa funcionalidade proporciona uma experiência visual moderna e imersiva, alinhada com as diretrizes de design do Android 10+ (API 29+).

## Visão Geral

As barras transparentes (edge-to-edge) no BaseApp permitem que o layout da aplicação ocupe toda a tela, com as barras de status e navegação ficando transparentes ou translúcidas, enquanto o conteúdo é desenhado por trás delas. Isso é implementado usando APIs do AndroidX e ajustes nos temas e layouts do projeto.

Os arquivos principais relacionados estão localizados em:

- `app/src/main/res/values/themes.xml`: Define o tema para barras transparentes.
- `app/src/main/res/values/themes-night.xml`: Ajustes para o modo escuro.
- `app/src/main/java/com/example/baseapp/ui/BaseUIActivity.java`: Configurações de código para ativar o modo edge-to-edge.
- `app/src/main/res/layout/activity_home.xml` (e outros layouts): Ajustes nos layouts para suportar barras transparentes.

## Configurando Barras Transparentes

1. **Ajustar o Tema**:
   - No arquivo `app/src/main/res/values/themes.xml`, configure o tema para suportar barras transparentes:
     ```xml
     <style name="AppTheme" parent="Theme.Material3.DayNight.NoActionBar">
         <item name="android:statusBarColor">@android:color/transparent</item>
         <item name="android:navigationBarColor">@android:color/transparent</item>
         <item name="android:windowTranslucentStatus">true</item>
         <item name="android:windowTranslucentNavigation">true</item>
     </style>
     ```
   - Certifique-se de que o tema base (`Theme.Material3.DayNight.NoActionBar`) é compatível com Material Design 3.

2. **Ativar Edge-to-Edge no Código**:
   - Em `BaseUIActivity.java`, configure a janela para o modo edge-to-edge:
     ```java
     import androidx.core.view.WindowCompat;
     import androidx.core.view.WindowInsetsCompat;
     import androidx.core.view.ViewCompat;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         // Ativar modo edge-to-edge
         WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
         // Ajustar padding para conteúdo sob as barras
         ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_layout), (v, insets) -> {
             int statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top;
             int navBarHeight = insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom;
             v.setPadding(0, statusBarHeight, 0, navBarHeight);
             return insets;
         });
     }
     ```

3. **Ajustar Layouts**:
   - Nos arquivos de layout (ex.: `activity_home.xml`, `activity_settings.xml`, `activity_profile.xml`), use `fitsSystemWindows="false"` no contêiner principal para permitir que o conteúdo se estenda sob as barras:
     ```xml
     <androidx.drawerlayout.widget.DrawerLayout
         android:id="@+id/drawer_layout"
         android:layout_width="match_parent"
         android:layout_height="match_parent"
         android:fitsSystemWindows="false">
         <!-- Conteúdo do layout -->
     </androidx.drawerlayout.widget.DrawerLayout>
     ```

## Personalização

- **Cores das Barras**:
  - Modifique `android:statusBarColor` e `android:navigationBarColor` em `themes.xml` para cores personalizadas (ex.: `@color/primary_color`).
  - Para gradientes ou efeitos translúcidos, use `@android:color/transparent` com um fundo personalizado no layout.

- **Ícones da Barra de Status**:
  - Para garantir que os ícones da barra de status sejam visíveis, ajuste o tema para modo claro ou escuro:
    ```xml
    <item name="android:windowLightStatusBar">true</item> <!-- Para ícones escuros -->
    ```

- **Adaptação ao Modo Escuro**:
  - Em `themes-night.xml`, configure cores específicas para o modo escuro:
    ```xml
    <style name="AppTheme" parent="Theme.Material3.DayNight.NoActionBar">
        <item name="android:statusBarColor">@android:color/transparent</item>
        <item name="android:navigationBarColor">@android:color/transparent</item>
        <item name="android:windowLightStatusBar">false</item> <!-- Ícones claros no modo escuro -->
    </style>
    ```

## Solução de Problemas

- **Conteúdo Sobreposto pelas Barras**:
  - Verifique se `WindowCompat.setDecorFitsSystemWindows(getWindow(), false)` está chamado em `BaseUIActivity.java`.
  - Confirme que `fitsSystemWindows="false"` está definido no layout principal.

- **Ícones da Barra de Status Invisíveis**:
  - Ajuste `android:windowLightStatusBar` em `themes.xml` para corresponder ao fundo (ex.: `true` para fundos claros, `false` para escuros).

- **Erro em APIs Antigas**:
  - Para compatibilidade com APIs abaixo de 29, use verificações condicionais:
    ```java
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
    }
    ```

## Próximos Passos

Para integrar as barras transparentes com outros recursos, consulte:
- [**Configuração de Navegação**](/docs/navigation.md) para ajustar a navegação com barras transparentes.
- [**Tema Dinâmico**](/docs/theme-switching.md) para sincronizar o comportamento das barras com o modo claro/escuro.