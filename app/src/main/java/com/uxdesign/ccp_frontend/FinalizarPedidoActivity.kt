package com.uxdesign.ccp_frontend

import android.app.DatePickerDialog
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
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.ParseException
import java.util.Calendar
import java.util.Date
import java.util.TimeZone

class FinalizarPedidoActivity : AppCompatActivity() {
    private lateinit var cliente: Cliente
    private lateinit var editCliente: EditText
    private lateinit var editFecha: EditText
    private lateinit var editNumProductos: EditText
    private lateinit var editTotal: EditText
    private lateinit var editComentarios: EditText
    private var modoEscalaGrises = false
    private var color: String? = "ORANGE"
    private lateinit var idUsuario: String
    private lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_finalizar_pedido)

        idUsuario = intent.getStringExtra("id_usuario") ?: ""
        token = intent.getStringExtra("token") ?: ""
        val cantidadProd = intent.getIntExtra("cantidad_productos", 0)
        val valorTotal = intent.getDoubleExtra("valor_total", 0.0)
        color = intent.getStringExtra("color")

        editCliente = findViewById(R.id.editCliente)
        editFecha = findViewById(R.id.editFechaEntrega)
        editFecha.setOnClickListener {
            val calendario = Calendar.getInstance()
            val year = calendario.get(Calendar.YEAR)
            val month = calendario.get(Calendar.MONTH)
            val day = calendario.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    val fechaSeleccionada = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay)
                    editFecha.setText(fechaSeleccionada)
                },
                year, month, day
            )

            datePicker.show()
        }

        editNumProductos = findViewById(R.id.editNumProductos)
        editTotal = findViewById(R.id.editTotal)
        editComentarios = findViewById(R.id.editComentarios)

        editNumProductos.setText(cantidadProd.toString())
        editTotal.setText("$${String.format("%.2f", valorTotal)}")

        obtenerClientePorId(idUsuario)


        //Adaptabilidad
        val mainLayout: ScrollView = findViewById(R.id.main)
        val titleCliente: TextView = findViewById(R.id.tituloCliente)
        val titleDate: TextView = findViewById(R.id.tituloFechaEntrega)
        val titleCantidad: TextView = findViewById(R.id.tituloNumProductos)
        val titleTotal: TextView = findViewById(R.id.tituloTotal)
        val titleComentarios: TextView = findViewById(R.id.tituloComentarios)
        val buttonRegistrar: Button = findViewById(R.id.buttonRegistrar)
        val imageEye: ImageView = findViewById(R.id.imageOjoN)

        if (color == "ORANGE") {
            mainLayout.setBackgroundColor(resources.getColor(R.color.orange, null))
            titleCliente.setTextColor(resources.getColor(R.color.orange, null))
            titleDate.setTextColor(resources.getColor(R.color.orange, null))
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
                titleCantidad.setTextColor(resources.getColor(R.color.orange, null))
                titleTotal.setTextColor(resources.getColor(R.color.orange, null))
                titleComentarios.setTextColor(resources.getColor(R.color.orange, null))
                buttonRegistrar.setBackgroundColor(resources.getColor(R.color.black, null))
                imageEye.setImageResource(R.drawable.pinkeye)
                color = "ORANGE"
            }
        }
        buttonRegistrar.setOnClickListener {

            if (!validarCampos()) {
                return@setOnClickListener
            }

            val fechaEntrega = editFecha.text.toString().trim()
            val fechaISO = convertirFechaAISO8601(fechaEntrega)
            val comentarios = editComentarios.text.toString().trim()

           val pedido = Pedido(
                idCliente = idUsuario,
                fechaEntrega = fechaISO,
                estadoPedido = "CREADO",
                valorTotal = valorTotal,
                idVendedor = null,
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

    private fun obtenerClientePorId(idUsuario: String?) {
        if (idUsuario == null) {
            Toast.makeText(this, "ID del producto no disponible", Toast.LENGTH_SHORT).show()
            return
        }
        val retrofit = Retrofit.Builder()
            .baseUrl("https://servicio-cliente-596275467600.us-central1.run.app/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        apiService.getClienteId(idUsuario).enqueue(object : Callback<SingleCliente> {
            override fun onResponse(call: Call<SingleCliente>, response: Response<SingleCliente>) {
                if (response.isSuccessful) {
                    val clientea = response.body()?.cliente
                    if (clientea != null) {
                        cliente = clientea
                        editCliente.setText("${cliente.nombre} ${cliente.apellido}")
                    } else {
                        findViewById<Button>(R.id.buttonRegistrar).isEnabled = false
                        findViewById<Button>(R.id.buttonRegistrar).alpha = 0.5f
                        return
                    }

                } else {
                    Toast.makeText(this@FinalizarPedidoActivity, "Error al cargar cliente", Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<SingleCliente>, t: Throwable) {
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
    private fun enviarPedido(pedido: Pedido, idUsuario: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://servicio-pedidos-596275467600.us-central1.run.app/api/")
            .client(client)
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
            .client(client)
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

    private fun convertirFechaAISO8601(fecha: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        outputFormat.timeZone = TimeZone.getTimeZone("UTC")

        val date: Date = inputFormat.parse(fecha)!!
        return outputFormat.format(date)
    }
}