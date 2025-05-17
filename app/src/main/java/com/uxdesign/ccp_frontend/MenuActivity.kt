package com.uxdesign.ccp_frontend

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.uxdesign.cpp.R

class MenuActivity : AppCompatActivity() {
    private lateinit var idUsuario: String
    private lateinit var token: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)

        idUsuario = intent.getStringExtra("id_usuario") ?: "desconocido"
        token = intent.getStringExtra("token") ?: ""

        val buttonCatalogo: Button = findViewById(R.id.buttonPedido)
        buttonCatalogo.setOnClickListener {
            val intent = Intent(this, CatalogoProductosActivity::class.java)
            intent.putExtra("id_usuario", idUsuario)
            intent.putExtra("token", token)
            startActivity(intent)
        }

        val buttonEstadoPedido: Button = findViewById(R.id.buttonConsultarEstadoPedido)
        buttonEstadoPedido.setOnClickListener {
            val intent = Intent(this, ConsultarEstadoPedidosActivity::class.java)
            intent.putExtra("id_usuario", idUsuario)
            intent.putExtra("token", token)
            startActivity(intent)
        }

        val buttonEntregaProgamada: Button = findViewById(R.id.buttonEntrega)
        buttonEntregaProgamada.setOnClickListener {
            val intent = Intent(this, EntregaProgramadaActivity::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}