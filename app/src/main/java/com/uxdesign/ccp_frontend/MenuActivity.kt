package com.uxdesign.ccp_frontend

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.uxdesign.cpp.R
import com.uxdesign.cpp.utils.TokenManager

class MenuActivity : AppCompatActivity() {
    private lateinit var idUsuario: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)

        idUsuario = intent.getStringExtra("id_usuario") ?: "desconocido"

        val buttonCatalogo: Button = findViewById(R.id.buttonPedido)
        buttonCatalogo.setOnClickListener {
            val intent = Intent(this, CatalogoProductosActivity::class.java)
            intent.putExtra("id_usuario", idUsuario)
            startActivity(intent)
        }

        val buttonEstadoPedido: Button = findViewById(R.id.buttonConsultarEstadoPedido)
        buttonEstadoPedido.setOnClickListener {
            val intent = Intent(this, ConsultarEstadoPedidosActivity::class.java)
            intent.putExtra("id_usuario", idUsuario)
            startActivity(intent)
        }

        val buttonEntregaProgamada: Button = findViewById(R.id.buttonEntrega)
        buttonEntregaProgamada.setOnClickListener {
            val intent = Intent(this, EntregaProgramadaActivity::class.java)
            intent.putExtra("id_usuario", idUsuario)
            startActivity(intent)
        }

        val buttonCerrarSesion: Button = findViewById(R.id.buttonCerrar)
        buttonCerrarSesion.setOnClickListener {
            TokenManager.clearToken(this)
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}