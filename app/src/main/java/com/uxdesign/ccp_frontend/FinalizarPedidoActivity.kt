package com.uxdesign.ccp_frontend

import android.content.Intent
import android.os.Bundle
import java.text.SimpleDateFormat
import java.util.Locale
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isEmpty
import com.uxdesign.cpp.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.ParseException

class FinalizarPedidoActivity : AppCompatActivity() {
    private lateinit var cliente: Cliente
    private lateinit var editCliente: EditText
    private lateinit var editFecha: EditText
    private lateinit var editHora: EditText
    private lateinit var editNumProductos: EditText
    private lateinit var editTotal: EditText
    private lateinit var editComentarios: EditText
    private var modoEscalaGrises = false
    private var color: String? = "ORANGE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_finalizar_pedido)

        val idUsuario = intent.getStringExtra("id_usuario") ?: "desconocido"
        val cantidadProd = intent.getIntExtra("cantidad_productos", 0)
        val valorTotal = intent.getDoubleExtra("valor_total", 0.0)
        color = intent.getStringExtra("color")

        editCliente = findViewById(R.id.editCliente)
        editFecha = findViewById(R.id.editFechaEntrega)
        editHora = findViewById(R.id.editHora)
        editNumProductos = findViewById(R.id.editNumProductos)
        editTotal = findViewById(R.id.editTotal)
        editComentarios = findViewById(R.id.editComentarios)

        editCliente.setText(idUsuario)
        editNumProductos.setText(cantidadProd.toString())
        editTotal.setText("$${String.format("%.2f", valorTotal)}")


        //Adaptabilidad
        val mainLayout: ScrollView = findViewById(R.id.main)
        val titleCliente: TextView = findViewById(R.id.tituloCliente)
        val titleDate: TextView = findViewById(R.id.tituloFechaEntrega)
        val titleHora: TextView = findViewById(R.id.tituloHora)
        val titleCantidad: TextView = findViewById(R.id.tituloNumProductos)
        val titleTotal: TextView = findViewById(R.id.tituloTotal)
        val titleComentarios: TextView = findViewById(R.id.tituloComentarios)
        val buttonRegistrar: Button = findViewById(R.id.buttonRegistrar)
        val imageEye: ImageView = findViewById(R.id.imageOjoN)

        if (color == "ORANGE") {
            mainLayout.setBackgroundColor(resources.getColor(R.color.orange, null))
            titleCliente.setTextColor(resources.getColor(R.color.orange, null))
            titleDate.setTextColor(resources.getColor(R.color.orange, null))
            titleHora.setTextColor(resources.getColor(R.color.orange, null))
            titleCantidad.setTextColor(resources.getColor(R.color.orange, null))
            titleTotal.setTextColor(resources.getColor(R.color.orange, null))
            titleComentarios.setTextColor(resources.getColor(R.color.orange, null))
            buttonRegistrar.setBackgroundColor(resources.getColor(R.color.black, null))
            imageEye.setImageResource(R.drawable.pinkeye)
            modoEscalaGrises = false
        }else{
            mainLayout.setBackgroundColor(resources.getColor(R.color.darkgrey, null))
            titleCliente.setTextColor(resources.getColor(R.color.darkgrey, null))
            titleDate.setTextColor(resources.getColor(R.color.darkgrey, null))
            titleHora.setTextColor(resources.getColor(R.color.darkgrey, null))
            titleCantidad.setTextColor(resources.getColor(R.color.darkgrey, null))
            titleTotal.setTextColor(resources.getColor(R.color.darkgrey, null))
            titleComentarios.setTextColor(resources.getColor(R.color.darkgrey, null))
            buttonRegistrar.setBackgroundColor(resources.getColor(R.color.greytext, null))
            imageEye.setImageResource(R.drawable.blackeye)
            modoEscalaGrises = true
        }

        imageEye.setOnClickListener {
            modoEscalaGrises = !modoEscalaGrises
            if (modoEscalaGrises == true) {
                mainLayout.setBackgroundColor(resources.getColor(R.color.darkgrey, null))
                titleCliente.setTextColor(resources.getColor(R.color.darkgrey, null))
                titleDate.setTextColor(resources.getColor(R.color.darkgrey, null))
                titleHora.setTextColor(resources.getColor(R.color.darkgrey, null))
                titleCantidad.setTextColor(resources.getColor(R.color.darkgrey, null))
                titleTotal.setTextColor(resources.getColor(R.color.darkgrey, null))
                titleComentarios.setTextColor(resources.getColor(R.color.darkgrey, null))
                buttonRegistrar.setBackgroundColor(resources.getColor(R.color.greytext, null))
                imageEye.setImageResource(R.drawable.blackeye)
                color = "GREY"
                Toast.makeText(this, "Se ha activado una ayuda visual", Toast.LENGTH_SHORT).show()
            } else {
                mainLayout.setBackgroundColor(resources.getColor(R.color.orange, null))
                titleCliente.setTextColor(resources.getColor(R.color.orange, null))
                titleDate.setTextColor(resources.getColor(R.color.orange, null))
                titleHora.setTextColor(resources.getColor(R.color.orange, null))
                titleCantidad.setTextColor(resources.getColor(R.color.orange, null))
                titleTotal.setTextColor(resources.getColor(R.color.orange, null))
                titleComentarios.setTextColor(resources.getColor(R.color.orange, null))
                buttonRegistrar.setBackgroundColor(resources.getColor(R.color.black, null))
                imageEye.setImageResource(R.drawable.pinkeye)
                color = "ORANGE"
            }
        }

        //--------------------------------------

        //cargarClienteDesdeApi(idUsuario)

        buttonRegistrar.setOnClickListener {

            if (!validarCampos()) {
                return@setOnClickListener
            }

            val fechaEntrega = editFecha.text.toString().trim()
            val hora = editHora.text.toString().trim()
            val comentarios = editComentarios.text.toString().trim()
            //val numProductos = editNumProductos.text.toString().trim().toInt()
            //val total = editTotal.text.toString().trim().toDouble()

           val pedido = Pedido(
                idCliente = idUsuario,
                fechaEntrega = fechaEntrega,
                estadoPedido = "CREADO",
                valorTotal = valorTotal,
                idVendedor = "",
                comentarios = comentarios,
                idMoneda = 11
            )

            enviarPedido(pedido, idUsuario)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun cargarClienteDesdeApi(idUsuario: String?) {
        if (idUsuario == null) {
            Toast.makeText(this, "ID del producto no disponible", Toast.LENGTH_SHORT).show()
            return
        }
        val retrofit = Retrofit.Builder()
            .baseUrl("https://inventarios-596275467600.us-central1.run.app/api/") // Cambia por tu URL real
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        apiService.getClienteId(idUsuario).enqueue(object : Callback<RespuestaCliente> {
            override fun onResponse(call: Call<RespuestaCliente>, response: Response<RespuestaCliente>) {
                if (response.isSuccessful) {
                    val clienter = response.body()?.clientes
                    //cliente = clienter.get(1)
                    findViewById<TextView>(R.id.editCliente).text = cliente.nombre

                    if (cliente == null) {
                        findViewById<Button>(R.id.buttonAgregar).isEnabled = false
                        findViewById<Button>(R.id.buttonAgregar).alpha = 0.5f
                    }

                } else {
                    findViewById<TextView>(R.id.textStock).text = "Stock: 0 unidades, producto no disponible"
                    findViewById<Button>(R.id.buttonAgregar).isEnabled = false
                    findViewById<Button>(R.id.buttonAgregar).alpha = 0.5f
                    findViewById<EditText>(R.id.editCantidad).isEnabled = false
                    //Toast.makeText(this@DetalleProductoActivity, "Error al cargar stock", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<RespuestaCliente>, t: Throwable) {
                findViewById<Button>(R.id.buttonRegistrar).isEnabled = false
                findViewById<Button>(R.id.buttonRegistrar).alpha = 0.5f
                Toast.makeText(this@FinalizarPedidoActivity, "Error de conexión con cliente", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun validarCampos(): Boolean {
        if (editCliente.toString().isEmpty() ) {
            showToast("No fue posible carga tu id como cliente")
            return false
        }

        if (editFecha.text.toString().trim().isEmpty()) {
            showToast("Ingrese la fecha de entrega")
            return false
        }

        if (!validarFecha(editFecha.text.toString().trim())) {
            showToast("La fecha de entrega debe tener el formato yyyy-MM-dd")
            return false
        }

        if (!validarHora(editHora.text.toString().trim())) {
            showToast("La hora debe tener el formato HH:MM")
            return false
        }


        if (editHora.text.toString().trim().isEmpty()) {
            showToast("Ingrese la hora")
            return false
        }

        if (editNumProductos.text.toString().trim().isEmpty()) {
            showToast("Número de productos es obligatorio")
            return false
        }

        if (editTotal.text.toString().trim().isEmpty()) {
            showToast("Total del pedido es obligatorio")
            return false
        }

        return true

    }

    fun validarHora(hora: String): Boolean {
        val regex = "^([01]?[0-9]|2[0-3]):([0-5]?[0-9])$".toRegex()
        return hora.matches(regex)
    }

    private fun validarFecha(fecha: String): Boolean {
        val formato = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        formato.isLenient = false

        return try {
            val date = formato.parse(fecha)

            fecha == formato.format(date)
        } catch (e: ParseException) {
            false
        }
    }

    private fun showToast(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    private fun enviarPedido(pedido: Pedido, idUsuario: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://servicio-pedidos-596275467600.us-central1.run.app/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        apiService.crearPedido(pedido).enqueue(object : Callback<RespuestaRequestPedido> {
            override fun onResponse(call: Call<RespuestaRequestPedido>, response: Response<RespuestaRequestPedido>) {
                if (response.isSuccessful) {
                    val respuesta = response.body()
                    val idPedido = respuesta?.id
                    if (idPedido != null){
                        asociarDetalles(idUsuario, idPedido)
                    } else {

                    Toast.makeText(this@FinalizarPedidoActivity, "No fue posible crear el pedido, intente de nuevo", Toast.LENGTH_SHORT).show()
                }

                } else {
                    Toast.makeText(this@FinalizarPedidoActivity, "Error al crear pedido", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<RespuestaRequestPedido>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@FinalizarPedidoActivity, "Error de conexión con pedido", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun asociarDetalles(idUsuario: String, idPedido: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://servicio-pedidos-596275467600.us-central1.run.app/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        apiService.enlazarDetallePedido(idUsuario, idPedido).enqueue(object : Callback<RespuestaRequestPedido> {
            override fun onResponse(call: Call<RespuestaRequestPedido>, response: Response<RespuestaRequestPedido>) {
                if (response.isSuccessful) {
                    val respuesta = response.body()
                    val status = respuesta?.status
                    if (status == 201){
                        Toast.makeText(this@FinalizarPedidoActivity, "El pedido ha sido registrado exitosamente", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@FinalizarPedidoActivity, CatalogoProductosActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    } else {

                        Toast.makeText(this@FinalizarPedidoActivity, "No fue posible agregar detalles, intente de nuevo", Toast.LENGTH_SHORT).show()
                    }

                } else {
                    Toast.makeText(this@FinalizarPedidoActivity, "Error al agregar detalles", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<RespuestaRequestPedido>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@FinalizarPedidoActivity, "Error de conexión con pedido", Toast.LENGTH_SHORT).show()
            }
        })
    }
}