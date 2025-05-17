package com.uxdesign.ccp_frontend

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
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

class ConsultarEstadoPedidosActivity : AppCompatActivity() {
    private val pedidos = mutableListOf<PedidoProcesado>()
    private lateinit var apiService: ApiService
    private lateinit var adapter: PedidoAdapter
    private lateinit var idUsuario: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_consultar_estado_pedidos)
        idUsuario = intent.getStringExtra("id_usuario") ?: ""

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewEstadoPedidos)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = PedidoAdapter(pedidos, idUsuario)
        recyclerView.adapter = adapter

        getPedidos(idUsuario)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

        private fun getPedidos(idUsuario: String?) {
        if (idUsuario.isNullOrEmpty()) {
            Toast.makeText(this, "ID de usuario no disponible", Toast.LENGTH_SHORT).show()
            return
        }
            val client = OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor(this))
                .build()

        val estado = "CONFIRMADO"
        val retrofit = Retrofit.Builder()
            .baseUrl("https://servicio-pedidos-596275467600.us-central1.run.app/api/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)

        apiService.getPedidosPorCliente(idUsuario, estado).enqueue(object : Callback<RespuestaPedidoProcesado> {
               override fun onResponse(call: Call<RespuestaPedidoProcesado>, response: Response<RespuestaPedidoProcesado>) {
                    if (response.isSuccessful) {
                        val pedidoList = response.body()?.pedidos
                        if (pedidoList != null) {
                            pedidos.clear()
                            pedidos.addAll(pedidoList)
                            adapter.notifyDataSetChanged()
                        }
                    } else {
                        Toast.makeText(this@ConsultarEstadoPedidosActivity, "Error al cargar los estados de pedidos", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<RespuestaPedidoProcesado>, t: Throwable) {
                    t.printStackTrace()
                    Toast.makeText(this@ConsultarEstadoPedidosActivity, "Error de conexión de pedidos", Toast.LENGTH_SHORT).show()
                }
          })

    }

}