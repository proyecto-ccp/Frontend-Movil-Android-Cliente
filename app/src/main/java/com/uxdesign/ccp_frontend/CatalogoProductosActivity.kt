package com.uxdesign.ccp_frontend

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uxdesign.ccp_frontend.helpers.CatalogoRepository
import com.uxdesign.cpp.R
import kotlinx.coroutines.launch

class CatalogoProductosActivity : AppCompatActivity() {
    private val productos = mutableListOf<Producto>()
    private lateinit var apiService: ApiService
    private var modoEscalaGrises = false
    private var color = "ORANGE"
    private var idUsuario = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_catalogo_productos)

        idUsuario = intent.getStringExtra("id_usuario") ?: "desconocido"

        //Adaptabilidad

        val mainLayout: ConstraintLayout = findViewById(R.id.main)
        val buttonPedido: Button = findViewById(R.id.botonPedido)
        val imageEye: ImageView = findViewById(R.id.imageOjoN)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewProductos)

        imageEye.setOnClickListener {
            modoEscalaGrises = !modoEscalaGrises
            if (modoEscalaGrises) {
                mainLayout.setBackgroundColor(resources.getColor(R.color.darkgrey, null))
                buttonPedido.setBackgroundColor(resources.getColor(R.color.greytext, null))
                imageEye.setImageResource(R.drawable.blackeye)
                color = "GREY"
                Toast.makeText(this, "Se ha activado una ayuda visual", Toast.LENGTH_SHORT).show()
            } else {
                mainLayout.setBackgroundColor(resources.getColor(R.color.orange, null))
                buttonPedido.setBackgroundColor(resources.getColor(R.color.black, null))
                imageEye.setImageResource(R.drawable.pinkeye)
                color = "ORANGE"
            }
            val adapter = ProductoAdapter(productos, color, idUsuario)
            recyclerView.adapter = adapter
        }

        buttonPedido.setOnClickListener {
            val intent = Intent(this, VerPedidoActivity::class.java)
            intent.putExtra("id_usuario", idUsuario)
            intent.putExtra("color", color )
            startActivity(intent)
        }

         recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = ProductoAdapter(productos, idUsuario, color)
        recyclerView.adapter = adapter

        getCatalogo()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun getCatalogo() {
        lifecycleScope.launch {
            try {
                val productosActualizados = CatalogoRepository.obtenerProductos(applicationContext)
                productos.clear()
                productos.addAll(productosActualizados)
                val adapter = ProductoAdapter(productos, color, idUsuario)
                findViewById<RecyclerView>(R.id.recyclerViewProductos).adapter = adapter
                adapter.notifyDataSetChanged()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@CatalogoProductosActivity, "No se pudo cargar el catálogo: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}