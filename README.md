# BaseApp

![BaseApp Logo](https://github.com/JhokoDev/BaseApp/blob/main/app/src/main/res/mipmap-xxxhdpi/ic_launcher_round.webp)

<p align="center">
  <a href="https://developer.android.com"><img alt="Android SDK 34" src="https://img.shields.io/badge/Android%20SDK-34-3DDC84?style=for-the-badge&logo=android"></a>
  <a href="https://www.java.com"><img alt="Java 17" src="https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=java"></a>
  <a href="https://kotlinlang.org"><img alt="Kotlin 1.9" src="https://img.shields.io/badge/Kotlin-1.9-0095D5?style=for-the-badge&logo=kotlin"></a>
  <a href="https://github.com/bumptech/glide"><img alt="Glide 4.16" src="https://img.shields.io/badge/Glide-4.16-40C4FF?style=for-the-badge"></a>
  <a href="https://material.io"><img alt="Material Design 1.12" src="https://img.shields.io/badge/Material%20Design-1.12-757575?style=for-the-badge&logo=material-design"></a>
</p>

## Sobre o BaseApp

**BaseApp** é um kit de iniciação (starter kit) para desenvolvimento de aplicações Android, projetado para fornecer uma base robusta, modular e escalável. Construído com **Java** e oferecendo suporte completo a **Kotlin**, o BaseApp incorpora funcionalidades essenciais, como navegação via menu hamburger, barras transparentes (edge-to-edge), gestão de perfil com upload de imagem circular, configurações de notificações e alternância de tema dinâmico (claro/escuro). Ele utiliza componentes **Material Design 3** e segue as melhores práticas do ecossistema Android, incluindo permissões dinâmicas e persistência de dados com `SharedPreferences`.

O objetivo do BaseApp é acelerar o desenvolvimento de aplicativos Android, oferecendo uma estrutura pronta para personalização, com foco em usabilidade, design moderno e compatibilidade com as APIs mais recentes do Android.

## Documentação

Toda a documentação detalhada sobre os recursos, padrões e arquitetura do BaseApp está disponível na pasta [`/docs`](/docs). Recomendamos a leitura para todos os desenvolvedores envolvidos no projeto:

- [**Configuração de Navegação**](/docs/navigation.md): Como configurar o menu hamburger e a `BottomNavigationView`.
- [**Barras Transparentes (Edge-to-Edge)**](/docs/edge-to-edge.md): Implementação de barras de status e navegação transparentes.
- [**Gestão de Perfil**](/docs/profile-management.md): Funcionalidades de edição de perfil e upload de imagem circular.
- [**Permissões Dinâmicas**](/docs/permissions.md): Gerenciamento de permissões para notificações e armazenamento.
- [**Notificações**](/docs/notifications.md): Configuração e disparo de notificações com ícone personalizado.
- [**Tema Dinâmico**](/docs/theme-switching.md): Alternância entre modos claro e escuro.

## Tecnologias Envolvidas

BaseApp utiliza uma stack moderna de desenvolvimento Android para garantir desempenho, escalabilidade e manutenibilidade:

- **Android SDK 34**: Acesso às APIs mais recentes para desenvolvimento Android.
- **Java 17**: Linguagem principal para lógica robusta e confiável.
- **Kotlin 1.9**: Suporte opcional para código conciso e seguro.
- **Glide 4.16**: Carregamento eficiente de imagens com recorte circular.
- **Material Design 3 (1.12)**: Componentes de UI modernos e consistentes.
- **Gradle 8.1**: Automação de build com suporte a Kotlin DSL.
- **RecyclerView 1.3.2**: Renderização eficiente de listas.
- **SharedPreferences**: Persistência leve de dados para configurações do usuário.
- **AndroidX Libraries**: Componentes Core, AppCompat e Lifecycle para compatibilidade e funcionalidade.

## Pré-requisitos

Antes de começar, certifique-se de ter instalado:

- **Android Studio**: Versão 2023.1+ (Giraffe ou superior) - [Download](https://developer.android.com/studio)
- **JDK**: Versão 17 ou superior - [Download](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
- **Git**: Para controle de versão - [Download](https://git-scm.com/)
- **Emulador ou Dispositivo Android**: API 23+ (recomendado API 33+ para permissões modernas)

## Instalação

Siga os passos abaixo para configurar o ambiente de desenvolvimento localmente:

### 1. Clonar o Repositório

Clone o repositório para sua máquina local:

```bash
git clone https://github.com/JhokoDev/BaseApp.git
cd BaseApp
```

### 2. Abrir no Android Studio

- Abra o projeto no Android Studio.
- Sincronize o Gradle: **File > Sync Project with Gradle Files**.

### 3. Configurar Dependências

Certifique-se de que o arquivo `app/build.gradle.kts` inclui as dependências necessárias:

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

### 4. Construir o Projeto

Limpe e reconstrua o projeto para garantir que todas as dependências sejam resolvidas:

```bash
./gradlew clean
./gradlew build
```

Se necessário, adicione `android.suppressUnsupportedCompileSdk=34` ao `gradle.properties` para suprimir avisos do AGP.

### 5. Executar o Aplicativo

- Selecione um emulador ou dispositivo (API 23+).
- Clique em **Run > Run 'app'**.
- Para testes de permissões modernas, use API 33+.

## Como Usar

### Tela Inicial (`HomeActivity`)

- Exibe uma mensagem de boas-vindas.
- Contém um FAB (FloatingActionButton) que exibe um `Toast` personalizável.
- Menu hamburger para navegação entre telas.
- `BottomNavigationView` como vitrine (itens "Item 1" e "Item 2").

### Tela de Configurações (`SettingsActivity`)

- **RecyclerView** com switches:
  - **Notificações**: Ativa/desativa notificações, solicita permissão (`POST_NOTIFICATIONS`) e dispara uma notificação de teste.
  - **Modo Escuro**: Alterna entre temas claro e escuro, persistindo a escolha em `SharedPreferences`.

### Tela de Perfil (`ProfileActivity`)

- **RecyclerView** com:
  - Exibição de nome e e-mail.
  - Imagem de perfil circular (usando Glide com `CircleCrop`).
  - Opção "Alterar Imagem de Perfil": Seleciona imagem da galeria, salva no armazenamento interno.
  - Opção "Editar Perfil": Atualiza nome e e-mail via diálogo.
  - Opção "Sair": Retorna à `HomeActivity`.

## Customização

Saiba como personalizar o BaseApp para atender às suas necessidades em [**Customização do Projeto**](/docs/customization.md).

## Estrutura do Projeto

Conheça a organização e arquitetura do BaseApp em [**Estrutura do Projeto**](/docs/project-structure.md).

## Solução de Problemas

Resolva questões comuns e otimize seu desenvolvimento com o BaseApp em [**Solução de Problemas**](/docs/troubleshooting.md).

## Recursos Atuais

- **Navegação Intuitiva**:
  - Menu hamburger para transições entre `HomeActivity`, `SettingsActivity` e `ProfileActivity`.
  - `BottomNavigationView` como vitrine, pronta para expansão.
- **Gestão de Perfil**:
  - Exibição de nome, e-mail e imagem circular com upload e persistência.
  - Edição de dados via diálogo.
  - Logout com navegação para a tela inicial.
- **Configurações**:
  - Ativação/desativação de notificações com permissão dinâmica.
  - Alternância de tema claro/escuro.
- **Permissões Dinâmicas**:
  - Suporte a `POST_NOTIFICATIONS` (API 33+).
  - Suporte a `READ_MEDIA_IMAGES` (API 33+) e `READ_EXTERNAL_STORAGE` (API < 33).
- **Notificações**:
  - Disparo de notificações de teste com ícone personalizado.

## Recursos em Desenvolvimento

- **Gestão de Mídias**:
  - CRUD para upload e gerenciamento de imagens, vídeos e documentos.
  - Visualização de mídias com suporte a diferentes formatos.
- **Autenticação Avançada**:
  - Suporte a login com redes sociais ou autenticação de dois fatores.
- **Widgets Personalizados**:
  - Exibição de métricas e informações dinâmicas na tela inicial.

## Recomendações de Ferramentas de Desenvolvimento

- **Android Studio**: IDE principal para desenvolvimento e depuração.
- **Gradle**: Sistema de build com suporte a Kotlin DSL.
- **Glide**: Biblioteca para carregamento e recorte de imagens.
- **Material Components**: Componentes de UI modernos e consistentes.
- **Lint**: Análise estática de código integrada ao Android Studio.
- **Git**: Controle de versão para colaboração e rastreamento de mudanças.

## Contribuição

1. Faça um fork do repositório.
2. Crie uma branch para sua feature:
   ```bash
   git checkout -b feature/nova-funcionalidade
   ```
3. Commit suas alterações:
   ```bash
   git commit -m "Adiciona nova funcionalidade"
   ```
4. Push para a branch:
   ```bash
   git push origin feature/nova-funcionalidade
   ```
5. Abra um Pull Request no GitHub.

## Licença

Este projeto está licenciado sob a [Licença MIT](LICENSE.md).

## Agradecimentos

Agradecemos às seguintes equipes e comunidades:

- **Android Team**: Pelo desenvolvimento do Android SDK e ferramentas robustas.
- **Google Material Design**: Pela biblioteca Material Components, que garante uma UI moderna e consistente.
- **Glide Contributors**: Pela biblioteca Glide, essencial para o recorte circular de imagens.
- **Comunidade Android**: Por tutoriais, fóruns e recursos que inspiram o desenvolvimento.

## Autor

- **JhokoDev** - [@JhokoDev](https://github.com/JhokoDev)

---

<div align="center">
  <strong>BaseApp - Construindo aplicações Android modernas e escaláveis</strong>
</div>