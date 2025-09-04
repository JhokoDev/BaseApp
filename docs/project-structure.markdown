# Estrutura do Projeto BaseApp

## Introdução

Este documento detalha a organização e arquitetura do **BaseApp**, fornecendo uma visão clara dos diretórios, arquivos e sua função no projeto. O BaseApp é estruturado para ser modular, escalável e fácil de manter, seguindo as melhores práticas de desenvolvimento Android. A compreensão dessa estrutura é essencial para desenvolvedores que desejam personalizar ou expandir o projeto.

## Visão Geral

O BaseApp segue a arquitetura padrão de um projeto Android, com uma separação clara entre código-fonte (`java`), recursos (`res`) e configurações de build (`gradle`). A estrutura foi projetada para suportar extensões e manutenção, com pastas organizadas por funcionalidade, como atividades, utilitários, adaptadores e modelos de dados.

Abaixo está a árvore de diretórios principal do projeto:

```
BaseApp/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   ├── com/example/baseapp/ui/
│   │   │   │   │   ├── BaseUIActivity.java
│   │   │   │   │   ├── HomeActivity.java
│   │   │   │   │   ├── SettingsActivity.java
│   │   │   │   │   ├── ProfileActivity.java
│   │   │   │   ├── com/example/baseapp/utils/
│   │   │   │   │   ├── NavigationUtils.java
│   │   │   │   │   ├── PermissionUtils.java
│   │   │   │   │   ├── NotificationUtils.java
│   │   │   │   ├── com/example/baseapp/adapter/
│   │   │   │   │   ├── SettingsAdapter.java
│   │   │   │   │   ├── ProfileAdapter.java
│   │   │   │   ├── com/example/baseapp/model/
│   │   │   │   │   ├── SettingItem.java
│   │   │   │   │   ├── ProfileItem.java
│   │   │   ├── res/
│   │   │   │   ├── layout/
│   │   │   │   │   ├── activity_home.xml
│   │   │   │   │   ├── activity_settings.xml
│   │   │   │   │   ├── activity_profile.xml
│   │   │   │   │   ├── item_setting.xml
│   │   │   │   │   ├── item_profile.xml
│   │   │   │   │   ├── nav_header.xml
│   │   │   │   │   ├── dialog_edit_profile.xml
│   │   │   │   ├── drawable/
│   │   │   │   │   ├── ic_notification.xml
│   │   │   │   │   ├── ic_profile.xml
│   │   │   │   │   ├── circle_background.xml
│   │   │   │   │   ├── ic_home.xml
│   │   │   │   │   ├── ic_settings.xml
│   │   │   │   │   ├── ic_item1.xml
│   │   │   │   │   ├── ic_item2.xml
│   │   │   │   ├── menu/
│   │   │   │   │   ├── drawer_menu.xml
│   │   │   │   │   ├── navigation_menu.xml
│   │   │   │   ├── values/
│   │   │   │   │   ├── strings.xml
│   │   │   │   │   ├── colors.xml
│   │   │   │   │   ├── themes.xml
│   │   │   │   │   ├── themes-night.xml
│   ├── build.gradle.kts
├── gradle/
├── gradle.properties
├── settings.gradle.kts
```

## Descrição das Pastas e Arquivos

### Raiz do Projeto
- **`app/`**: Contém o código-fonte e recursos do aplicativo.
- **`gradle/`**: Inclui scripts e configurações do Gradle para automação de build.
- **`gradle.properties`**: Configurações globais do Gradle (ex.: supressão de avisos do AGP).
- **`settings.gradle.kts`**: Define o nome do projeto e módulos incluídos.

### Código-Fonte (`app/src/main/java/`)
- **`com/example/baseapp/ui/`**:
  - `BaseUIActivity.java`: Atividade base que contém lógica compartilhada para navegação, FAB e configurações de UI (ex.: barras transparentes).
  - `HomeActivity.java`: Tela inicial com mensagem de boas-vindas, FAB e `BottomNavigationView`.
  - `SettingsActivity.java`: Tela de configurações com `RecyclerView` para opções como notificações e modo escuro.
  - `ProfileActivity.java`: Tela de perfil com `RecyclerView` para exibir/editar dados do usuário.
- **`com/example/baseapp/utils/`**:
  - `NavigationUtils.java`: Métodos utilitários para gerenciar navegação entre atividades.
  - `PermissionUtils.java`: Funções para verificar e solicitar permissões dinâmicas.
  - `NotificationUtils.java`: Funções para criar e gerenciar notificações.
- **`com/example/baseapp/adapter/`**:
  - `SettingsAdapter.java`: Adaptador para o `RecyclerView` da `SettingsActivity`.
  - `ProfileAdapter.java`: Adaptador para o `RecyclerView` da `ProfileActivity`.
- **`com/example/baseapp/model/`**:
  - `SettingItem.java`: Modelo de dados para itens de configurações.
  - `ProfileItem.java`: Modelo de dados para itens de perfil.

### Recursos (`app/src/main/res/`)
- **`layout/`**:
  - `activity_home.xml`: Layout da tela inicial.
  - `activity_settings.xml`: Layout da tela de configurações.
  - `activity_profile.xml`: Layout da tela de perfil.
  - `item_setting.xml`: Layout de cada item no `RecyclerView` de configurações.
  - `item_profile.xml`: Layout de cada item no `RecyclerView` de perfil.
  - `nav_header.xml`: Layout do cabeçalho do menu hamburger.
  - `dialog_edit_profile.xml`: Layout do diálogo de edição de perfil.
- **`drawable/`**:
  - Contém ícones vetoriais, como `ic_notification.xml`, `ic_profile.xml`, `ic_home.xml`, `ic_settings.xml`, `ic_item1.xml`, `ic_item2.xml`, e `circle_background.xml` para estilização.
- **`menu/`**:
  - `drawer_menu.xml`: Itens do menu hamburger.
  - `navigation_menu.xml`: Itens da `BottomNavigationView`.
- **`values/`**:
  - `strings.xml`: Strings de texto usadas no aplicativo.
  - `colors.xml`: Definições de cores para temas.
  - `themes.xml`: Tema claro do aplicativo.
  - `themes-night.xml`: Tema escuro do aplicativo.

### Configurações de Build
- **`app/build.gradle.kts`**: Define dependências (ex.: AndroidX, Glide, Material Design) e configurações de compilação.

## Boas Práticas para Extensão
- **Adicionar Novas Atividades**:
  - Crie novas classes em `com/example/baseapp/ui/` e herde de `BaseUIActivity.java` para manter consistência na navegação e UI.
- **Adicionar Recursos**:
  - Coloque novos layouts em `layout/`, ícones em `drawable/` e menus em `menu/`.
- **Manter Modularidade**:
  - Use `utils/` para funções reutilizáveis e `adapter/` para novos adaptadores de `RecyclerView`.
- **Documentação**:
  - Atualize este documento ao adicionar novas pastas ou arquivos significativos.

## Solução de Problemas

- **Arquivo Não Encontrado**:
  - Verifique se o caminho do arquivo corresponde à estrutura acima.
  - Confirme que o arquivo está incluído no `build.gradle.kts` (para recursos ou dependências).

- **Erro de Compilação por Recursos Ausentes**:
  - Certifique-se de que ícones (ex.: `ic_home.xml`) estão em `drawable/` e referenciados corretamente nos layouts ou menus.

- **Organização Confusa**:
  - Mantenha novos arquivos em pastas correspondentes (ex.: novos utilitários em `utils/`) para preservar a modularidade.

## Próximos Passos

Para explorar como personalizar ou estender a estrutura, consulte:
- [**Customização**](/docs/customization.md) para adicionar novos itens ou funcionalidades.
- [**Configuração de Navegação**](/docs/navigation.md) para entender a integração com menus e atividades.