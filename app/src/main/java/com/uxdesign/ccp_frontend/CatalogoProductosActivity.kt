package com.uxdesign.ccp_frontend

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uxdesign.cpp.R
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CatalogoProductosActivity : AppCompatActivity() {
    private val productos = mutableListOf<Producto>()
    private lateinit var apiService: ApiService
    private var modoEscalaGrises = false
    private var color = "ORANGE"
    private var idUsuario = ""
    private var token = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_catalogo_productos)

        idUsuario = intent.getStringExtra("id_usuario") ?: "desconocido"
        token = intent.getStringExtra("token") ?: ""

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
            intent.putExtra("token", token )
            startActivity(intent)
        }

         recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = ProductoAdapter(productos, idUsuario, color)
        recyclerView.adapter = adapter

        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .header("Authorization", "Bearer $token")
                    .method(original.method, original.body)
                    .build()
                chain.proceed(request)
            }
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://productos-596275467600.us-central1.run.app/api/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)

        getCatalogo()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun getCatalogo() {
        apiService.getProductos().enqueue(object : Callback<RespuestaProducto> {
            override fun onResponse(call: Call<RespuestaProducto>, response: Response<RespuestaProducto>) {
                if (response.isSuccessful) {
                    val productoList = response.body()?.productos ?: emptyList()
                    if (productoList != null) {
                        productos.clear()
                        productos.addAll(productoList)
                        val adapter = ProductoAdapter(productos, color, idUsuario)
                        findViewById<RecyclerView>(R.id.recyclerViewProductos).adapter = adapter
                        adapter.notifyDataSetChanged()
                    }
                } else {
                    Toast.makeText(this@CatalogoProductosActivity, "Error al cargar el catálogo", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<RespuestaProducto>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@CatalogoProductosActivity, "Error de conexión en catálogo", Toast.LENGTH_SHORT).show()
            }
        })
    }
}