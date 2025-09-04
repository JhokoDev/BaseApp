# Customização do BaseApp

## Introdução

Este documento detalha como personalizar o **BaseApp** para atender a necessidades específicas, oferecendo uma estrutura flexível e modular. Como um starter kit, o BaseApp é projetado para ser expandido, permitindo a adição de novos itens a listas, implementação de ações em componentes de navegação e extensão de funcionalidades como o Floating Action Button (FAB). As personalizações são implementadas principalmente nas atividades principais (`HomeActivity`, `SettingsActivity`, `ProfileActivity`) e classes utilitárias.

## Visão Geral

O BaseApp fornece pontos de extensão claros para customização, focando em usabilidade e escalabilidade. As principais áreas incluem:
- Adicionar itens a `RecyclerView` em telas como configurações e perfil.
- Implementar ações para itens da `BottomNavigationView`.
- Expandir a funcionalidade do FAB em cada atividade.
- Ajustes em layouts, adapters e modelos de dados para novas features.

Os arquivos principais para customização estão localizados em:
- `app/src/main/java/com/example/baseapp/ui/BaseUIActivity.java`: Base para extensões de UI, incluindo FAB e `BottomNavigationView`.
- `app/src/main/java/com/example/baseapp/ui/SettingsActivity.java`: Customização de configurações via `RecyclerView`.
- `app/src/main/java/com/example/baseapp/ui/ProfileActivity.java`: Customização de perfil via `RecyclerView`.
- `app/src/main/java/com/example/baseapp/adapter/SettingsAdapter.java`: Adaptador para itens de configurações.
- `app/src/main/java/com/example/baseapp/adapter/ProfileAdapter.java`: Adaptador para itens de perfil.
- `app/src/main/res/layout/activity_home.xml` (e outros layouts): Pontos de entrada para ajustes visuais.

## Adicionando Itens ao RecyclerView

1. **Em SettingsActivity**:
   - Adicione novos itens à lista de configurações em `SettingsActivity.java`:
     ```java
     List<SettingItem> settingItems = new ArrayList<>();
     settingItems.add(new SettingItem("Notificações", true)); // Item existente
     settingItems.add(new SettingItem("Modo Escuro", false)); // Item existente
     settingItems.add(new SettingItem("Novo Recurso", true)); // Novo item
     SettingsAdapter adapter = new SettingsAdapter(settingItems, this);
     recyclerView.setAdapter(adapter);
     ```

2. **Em ProfileActivity**:
   - Adicione novos itens ao perfil em `ProfileActivity.java`:
     ```java
     List<ProfileItem> profileItems = new ArrayList<>();
     profileItems.add(new ProfileItem("Nome", getNameFromSharedPreferences()));
     profileItems.add(new ProfileItem("E-mail", getEmailFromSharedPreferences()));
     profileItems.add(new ProfileItem("Alterar Imagem de Perfil", ""));
     profileItems.add(new ProfileItem("Editar Perfil", ""));
     profileItems.add(new ProfileItem("Telefone", getPhoneFromSharedPreferences())); // Novo item
     profileItems.add(new ProfileItem("Sair", ""));
     ProfileAdapter adapter = new ProfileAdapter(profileItems, this);
     recyclerView.setAdapter(adapter);
     ```

3. **Atualizar o Adapter**:
   - Em `SettingsAdapter.java` ou `ProfileAdapter.java`, ajuste o `onBindViewHolder` para lidar com novos itens:
     ```java
     @Override
     public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
         SettingItem item = items.get(position);
         holder.textView.setText(item.getName());
         holder.switchView.setChecked(item.isEnabled());
         holder.switchView.setOnCheckedChangeListener((buttonView, isChecked) -> {
             if ("Novo Recurso".equals(item.getName())) {
                 // Lógica personalizada para o novo recurso
                 toggleNewFeature(isChecked);
             }
         });
     }
     ```

## Implementando Ações para BottomNavigationView

- Em `BaseUIActivity.java`, expanda o listener da `BottomNavigationView` para ações personalizadas:
  ```java
  BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
  bottomNav.setOnItemSelectedListener(item -> {
      switch (item.getItemId()) {
          case R.id.nav_item1:
              // Ação personalizada para Item 1
              openCustomFeature1();
              return true;
          case R.id.nav_item2:
              // Ação personalizada para Item 2
              openCustomFeature2();
              return true;
          default:
              return false;
      }
  });
  ```

- Adicione novos itens ao menu em `app/src/main/res/menu/navigation_menu.xml`:
  ```xml
  <item
      android:id="@+id/nav_item3"
      android:title="Item 3"
      android:icon="@drawable/ic_item3" />
  ```

## Expandindo a Funcionalidade do FAB

- Em cada atividade (ex.: `HomeActivity.java`), personalize o FAB:
  ```java
  FloatingActionButton fab = findViewById(R.id.fab);
  fab.setOnClickListener(view -> {
      // Funcionalidade personalizada
      Snackbar.make(view, "Ação personalizada executada", Snackbar.LENGTH_LONG)
          .setAction("Desfazer", v -> undoCustomAction())
          .show();
  });
  ```

- Ajuste o ícone ou visibilidade do FAB em layouts como `activity_home.xml`:
  ```xml
  <com.google.android.material.floatingactionbutton.FloatingActionButton
      android:id="@+id/fab"
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:src="@drawable/ic_custom_fab"
      app:fabSize="normal" />
  ```

## Dicas Gerais para Customização

- **Persistência de Dados**: Use `SharedPreferences` para salvar estados personalizados, ou migre para Room para dados mais complexos.
- **Integração com Outros Recursos**: Consulte documentos relacionados para combinar customizações (ex.: adicionar permissões para novos recursos).
- **Testes**: Teste customizações em diferentes APIs (23+), especialmente para temas e permissões.
- **Boas Práticas**: Mantenha a modularidade adicionando novas classes utilitárias em `utils/` e adapters em `adapter/`.

## Solução de Problemas

- **Item Novo Não Aparece no RecyclerView**:
  - Verifique se o item foi adicionado à lista em `SettingsActivity.java` ou `ProfileActivity.java`.
  - Confirme que o adapter foi notificado com `notifyDataSetChanged()`.

- **Ação da BottomNavigationView Não Funciona**:
  - Certifique-se de que o ID do item em `navigation_menu.xml` corresponde ao usado no listener em `BaseUIActivity.java`.

- **FAB Não Responde**:
  - Verifique se o `setOnClickListener` está configurado corretamente.
  - Confirme que o FAB está visível no layout (ex.: `android:visibility="visible"`).

## Próximos Passos

Para aprofundar em customizações relacionadas, consulte:
- [**Configuração de Navegação**](/docs/navigation.md) para expandir o menu hamburger ou `BottomNavigationView`.
- [**Gestão de Perfil**](/docs/profile-management.md) para personalizar itens do perfil.
- [**Estrutura do Projeto**](/docs/project-structure.md) para entender onde adicionar novas classes e arquivos.