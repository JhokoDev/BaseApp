package com.example.baseapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.baseapp.UI.HomeActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Operações não ligadas a UI (ex: inicializar serviços, verificar permissões)
        initServices()

        // Lançar a tela inicial de UI (MenuActivity)
        startActivity(Intent(this, HomeActivity::class.java))
        finish() // Finalizar MainActivity para não ficar na stack
    }

    private fun initServices() {
        // Adicione lógica não UI aqui, ex: inicializar Firebase, verificar atualizações, etc.
    }
}