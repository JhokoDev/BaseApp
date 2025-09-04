# Configuração de Navegação no BaseApp

## Introdução

Este documento descreve como configurar e personalizar a navegação no **BaseApp**, incluindo o **menu hamburger** (implementado com `NavigationView`) e a **BottomNavigationView**. A navegação é um componente central do projeto, permitindo transições suaves entre as telas principais (`HomeActivity`, `SettingsActivity`, `ProfileActivity`) e oferecendo uma vitrine expansível para funcionalidades adicionais.

## Estrutura da Navegação

A navegação no BaseApp é gerenciada por duas estruturas principais:

- **Menu Hamburger**: Localizado em um `DrawerLayout`, acessível por um ícone de menu na barra de ação. Ele permite navegar entre as telas principais do aplicativo.
- **BottomNavigationView**: Exibe itens de vitrine (ex.: "Item 1" e "Item 2") na parte inferior da tela, ideal para acesso rápido a funcionalidades específicas.

Os arquivos principais relacionados à navegação estão localizados em:

- `app/src/main/res/menu/drawer_menu.xml`: Define os itens do menu hamburger.
- `app/src/main/res/menu/navigation_menu.xml`: Define os itens da `BottomNavigationView`.
- `app/src/main/java/com/example/baseapp/ui/BaseUIActivity.java`: Contém a lógica base para navegação.
- `app/src/main/java/com/example/baseapp/utils/NavigationUtils.java`: Utilitários para gerenciar transições e configurações de navegação.

## Configurando o Menu Hamburger

1. **Definir Itens do Menu**:
   - Edite o arquivo `app/src/main/res/menu/drawer_menu.xml` para adicionar ou modificar itens do menu. Exemplo:
     ```xml
     <menu xmlns:android="http://schemas.android.com/apk/res/android">
         <item
             android:id="@+id/nav_home"
             android:title="Início" />
         <item
             android:id="@+id/nav_settings"
             android:title="Configurações" />
         <item
             android:id="@+id/nav_profile"
             android:title="Perfil" />
     </menu>
     ```
   - Cada item corresponde a uma tela (`HomeActivity`, `SettingsActivity`, `ProfileActivity`).

2. **Configurar o DrawerLayout**:
   - Em `BaseUIActivity.java`, o `DrawerLayout` é inicializado e vinculado à `NavigationView`. Exemplo:
     ```java
     DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
     NavigationView navigationView = findViewById(R.id.nav_view);
     ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
         this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
     drawerLayout.addDrawerListener(toggle);
     toggle.syncState();
     navigationView.setNavigationItemSelectedListener(this);
     ```

3. **Implementar a Navegação**:
   - No método `onNavigationItemSelected` de `BaseUIActivity.java`, adicione a lógica para iniciar as atividades correspondentes:
     ```java
     @Override
     public boolean onNavigationItemSelected(@NonNull MenuItem item) {
         switch (item.getItemId()) {
             case R.id.nav_home:
                 startActivity(new Intent(this, HomeActivity.class));
                 break;
             case R.id.nav_settings:
                 startActivity(new Intent(this, SettingsActivity.class));
                 break;
             case R.id.nav_profile:
                 startActivity(new Intent(this, ProfileActivity.class));
                 break;
         }
         drawerLayout.closeDrawer(GravityCompat.START);
         return true;
     }
     ```

## Configurando a BottomNavigationView

1. **Definir Itens da BottomNavigationView**:
   - Edite `app/src/main/res/menu/navigation_menu.xml` para configurar os itens exibidos. Exemplo:
     ```xml
     <menu xmlns:android="http://schemas.android.com/apk/res/android">
         <item
             android:id="@+id/nav_item1"
             android:title="Item 1"
             android:icon="@drawable/ic_item1" />
         <item
             android:id="@+id/nav_item2"
             android:title="Item 2"
             android:icon="@drawable/ic_item2" />
     </menu>
     ```

2. **Vincular a BottomNavigationView**:
   - Em `BaseUIActivity.java`, configure a `BottomNavigationView`:
     ```java
     BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
     bottomNav.setOnItemSelectedListener(item -> {
         switch (item.getItemId()) {
             case R.id.nav_item1:
                 // Ação para Item 1
                 Toast.makeText(this, "Item 1 selecionado", Toast.LENGTH_SHORT).show();
                 return true;
             case R.id.nav_item2:
                 // Ação para Item 2
                 Toast.makeText(this, "Item 2 selecionado", Toast.LENGTH_SHORT).show();
                 return true;
         }
         return false;
     });
     ```

## Personalização

- **Adicionar Novos Itens**:
  - Para o menu hamburger, adicione novos `<item>` em `drawer_menu.xml` e implemente a lógica correspondente em `onNavigationItemSelected`.
  - Para a `BottomNavigationView`, adicione novos `<item>` em `navigation_menu.xml` e atualize o listener em `BaseUIActivity.java`.

- **Ícones e Estilos**:
  - Adicione ícones personalizados em `app/src/main/res/drawable/` (ex.: `ic_item3.xml`) e associe-os aos itens do menu.
  - Ajuste o tema em `app/src/main/res/values/themes.xml` para personalizar cores e estilos da navegação.

- **Utilitários de Navegação**:
  - Use métodos em `NavigationUtils.java` para simplificar transições, como:
    ```java
    public static void navigateToActivity(Context context, Class<?> activityClass) {
        Intent intent = new Intent(context, activityClass);
        context.startActivity(intent);
    }
    ```

## Solução de Problemas

- **Menu Hamburger Não Abre**:
  - Verifique se o `DrawerLayout` está corretamente configurado em `activity_home.xml` (ou outro layout relevante).
  - Confirme que o `ActionBarDrawerToggle` está sincronizado.

- **Itens da BottomNavigationView Não Respondem**:
  - Certifique-se de que o `setOnItemSelectedListener` está implementado corretamente.
  - Verifique se os IDs em `navigation_menu.xml` correspondem aos usados no código.

- **Ícones Ausentes**:
  - Confirme que os arquivos de ícones (ex.: `ic_item1.xml`, `ic_item2.xml`) estão em `app/src/main/res/drawable/`.

## Próximos Passos

Para personalizar ainda mais a navegação, consulte os documentos relacionados:
- [**Barras Transparentes (Edge-to-Edge)**](/docs/edge-to-edge.md) para integrar a navegação com barras transparentes.
- [**Tema Dinâmico**](/docs/theme-switching.md) para ajustar a aparência da navegação.