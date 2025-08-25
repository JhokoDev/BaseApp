package com.example.baseapp


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.baseapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        // Configurar o NavController com o NavHostFragment
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        val navController = navHostFragment.navController

        // Configurar BottomNavigationView com NavController
        activityMainBinding.navigation.setupWithNavController(navController)
        activityMainBinding.navigation.setOnNavigationItemReselectedListener {
            // Ignorar reseleção para evitar recarregar o mesmo fragmento
        }

        // Botão opcional para abrir outra atividade (descomente se necessário)
        /*
        val openStatusButton = findViewById<Button>(R.id.btn_open_status)
        openStatusButton.setOnClickListener {
            val intent = Intent(this, AnotherActivity::class.java)
            startActivity(intent)
        }
        */
    }

    override fun onBackPressed() {
        finish()
    }
}