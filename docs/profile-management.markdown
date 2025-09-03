# Gestão de Perfil no BaseApp

## Introdução

Este documento descreve como implementar e personalizar a funcionalidade de gestão de perfil no **BaseApp**, incluindo a exibição de informações do usuário, upload de imagem de perfil circular e edição de dados pessoais. A gestão de perfil é implementada na `ProfileActivity`, utilizando um `RecyclerView` para exibir e gerenciar os dados do usuário, com suporte a persistência via `SharedPreferences` e integração com a biblioteca **Glide** para imagens.

## Visão Geral

A funcionalidade de gestão de perfil permite que o usuário:
- Visualize seu nome, e-mail e imagem de perfil circular.
- Altere a imagem de perfil selecionando uma imagem da galeria.
- Edite nome e e-mail por meio de um diálogo.
- Realize logout, retornando à `HomeActivity`.

Os arquivos principais relacionados estão localizados em:
- `app/src/main/java/com/example/baseapp/ui/ProfileActivity.java`: Lógica principal da tela de perfil.
- `app/src/main/java/com/example/baseapp/adapter/ProfileAdapter.java`: Adaptador para o `RecyclerView` da tela de perfil.
- `app/src/main/java/com/example/baseapp/model/ProfileItem.java`: Modelo de dados para itens do perfil.
- `app/src/main/res/layout/activity_profile.xml`: Layout da tela de perfil.
- `app/src/main/res/layout/item_profile.xml`: Layout de cada item do `RecyclerView`.

## Configurando a Gestão de Perfil

1. **Exibir Informações do Usuário**:
   - Em `ProfileActivity.java`, o `RecyclerView` é configurado para exibir os dados do perfil:
     ```java
     RecyclerView recyclerView = findViewById(R.id.profile_recycler_view);
     recyclerView.setLayoutManager(new LinearLayoutManager(this));
     List<ProfileItem> profileItems = new ArrayList<>();
     profileItems.add(new ProfileItem("Nome", getNameFromSharedPreferences()));
     profileItems.add(new ProfileItem("E-mail", getEmailFromSharedPreferences()));
     profileItems.add(new ProfileItem("Alterar Imagem de Perfil", ""));
     profileItems.add(new ProfileItem("Editar Perfil", ""));
     profileItems.add(new ProfileItem("Sair", ""));
     ProfileAdapter adapter = new ProfileAdapter(profileItems, this);
     recyclerView.setAdapter(adapter);
     ```

2. **Carregar a Imagem de Perfil Circular**:
   - Use a biblioteca **Glide** com `CircleCrop` para exibir a imagem de perfil:
     ```java
     import com.bumptech.glide.Glide;
     import com.bumptech.glide.load.resource.bitmap.CircleCrop;

     ImageView profileImageView = findViewById(R.id.profile_image);
     Glide.with(this)
         .load(getProfileImagePathFromSharedPreferences())
         .transform(new CircleCrop())
         .placeholder(R.drawable.ic_profile)
         .into(profileImageView);
     ```

3. **Upload de Imagem de Perfil**:
   - Implemente a seleção de imagem da galeria e salve o caminho no `SharedPreferences`:
     ```java
     private static final int PICK_IMAGE_REQUEST = 1;

     public void openGallery() {
         Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
         startActivityForResult(intent, PICK_IMAGE_REQUEST);
     }

     @Override
     protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
         super.onActivityResult(requestCode, resultCode, data);
         if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
             Uri imageUri = data.getData();
             saveImageToInternalStorage(imageUri);
             updateProfileImage(imageUri);
         }
     }
     ```

4. **Editar Nome e E-mail**:
   - Use um diálogo para atualizar os dados do usuário:
     ```java
     public void showEditProfileDialog() {
         AlertDialog.Builder builder = new AlertDialog.Builder(this);
         LayoutInflater inflater = getLayoutInflater();
         View dialogView = inflater.inflate(R.layout.dialog_edit_profile, null);
         EditText nameInput = dialogView.findViewById(R.id.edit_name);
         EditText emailInput = dialogView.findViewById(R.id.edit_email);
         nameInput.setText(getNameFromSharedPreferences());
         emailInput.setText(getEmailFromSharedPreferences());
         builder.setView(dialogView)
             .setPositiveButton("Salvar", (dialog, which) -> {
                 saveProfileData(nameInput.getText().toString(), emailInput.getText().toString());
                 updateProfileRecyclerView();
             })
             .setNegativeButton("Cancelar", null)
             .show();
     }
     ```

5. **Logout**:
   - Implemente o logout retornando à `HomeActivity`:
     ```java
     public void performLogout() {
         startActivity(new Intent(this, HomeActivity.class));
         finish();
     }
     ```

## Personalização

- **Adicionar Novos Campos ao Perfil**:
  - Adicione novos `ProfileItem` à lista em `ProfileActivity.java` (ex.: "Telefone", "Endereço").
  - Atualize o `ProfileAdapter` para lidar com novos tipos de itens.

- **Estilizar a Imagem de Perfil**:
  - Modifique o `CircleCrop` do Glide para outros efeitos (ex.: bordas, sombras) em `ProfileActivity.java`.
  - Ajuste o layout em `item_profile.xml` para personalizar o tamanho ou estilo da imagem.

- **Persistência Avançada**:
  - Substitua `SharedPreferences` por um banco de dados (ex.: Room) para armazenar dados do perfil de forma mais robusta.

## Solução de Problemas

- **Imagem de Perfil Não Carrega**:
  - Verifique se a dependência do Glide está corretamente configurada em `app/build.gradle.kts`.
  - Confirme que o caminho da imagem em `SharedPreferences` é válido.

- **Diálogo de Edição Não Aparece**:
  - Certifique-se de que o layout `dialog_edit_profile.xml` existe em `app/src/main/res/layout/`.
  - Verifique se o método `showEditProfileDialog` está sendo chamado corretamente.

- **Permissões de Galeria Negadas**:
  - Consulte [**Permissões Dinâmicas**](/docs/permissions.md) para configurar `READ_MEDIA_IMAGES` (API 33+) ou `READ_EXTERNAL_STORAGE` (API < 33).

## Próximos Passos

Para integrar a gestão de perfil com outros recursos, consulte:
- [**Permissões Dinâmicas**](/docs/permissions.md) para gerenciar permissões de acesso à galeria.
- [**Tema Dinâmico**](/docs/theme-switching.md) para sincronizar a UI do perfil com o modo claro/escuro.