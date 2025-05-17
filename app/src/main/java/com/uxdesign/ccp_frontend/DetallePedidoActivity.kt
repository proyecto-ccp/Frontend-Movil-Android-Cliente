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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetallePedidoActivity : AppCompatActivity() {
    private val productos = mutableListOf<ProductoCarritoDetalle>()
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle_pedido)

        val pedidoId = intent.getStringExtra("pedido_id")

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewProductosPedido)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = ProductoPedidoDetalleAdapter(productos)
        recyclerView.adapter = ProductoPedidoDetalleAdapter(productos)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://servicio-pedidos-596275467600.us-central1.run.app/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)

        pedidoId?.let {
            getProductosPorPedido(it)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun getProductosPorPedido(pedidoId: String) {
        if (pedidoId.isNullOrEmpty()) {
            Toast.makeText(this, "ID de pedido no disponible", Toast.LENGTH_SHORT).show()
            return
        }

        apiService.getDetallePedido(pedidoId).enqueue(object : Callback<RespuestaDetallePedido> {
            override fun onResponse(call: Call<RespuestaDetallePedido>, response: Response<RespuestaDetallePedido>) {
                if (response.isSuccessful) {
                    val detalleList = response.body()?.detallePedidos
                    if (detalleList != null) {
                        productos.clear()
                        productos.addAll(detalleList)
                        (findViewById<RecyclerView>(R.id.recyclerViewProductosPedido).adapter as ProductoPedidoDetalleAdapter).notifyDataSetChanged()
                    }
                } else {
                    Toast.makeText(this@DetallePedidoActivity, "Error al cargar los productos de pedidos", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<RespuestaDetallePedido>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@DetallePedidoActivity, "Error de conexión de detalle pedido", Toast.LENGTH_SHORT).show()
            }
        })

    }
}