package com.uxdesign.ccp_frontend

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.uxdesign.cpp.R
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Callback
import retrofit2.Response


class DetalleProductoActivity : AppCompatActivity() {
    private var productoPrecio: Double = 0.0
    private var stockDisponible: Int = 0
    private var modoEscalaGrises = false
    private var color: String? = "ORANGE"
    private var idUsuario = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle_producto)

        val editCantidad: EditText = findViewById(R.id.editCantidad)
        val editValor: EditText = findViewById(R.id.editValor)

        //Adaptabilidad

        val mainLayout: ConstraintLayout = findViewById(R.id.main)
        val titleCanti: TextView = findViewById(R.id.tituloCantidad)
        val titleValue: TextView = findViewById(R.id.tituloValor)
        val agregarButton: Button = findViewById(R.id.buttonAgregar)
        val imageEye: ImageView = findViewById(R.id.imageOjoN)

        color = intent.getStringExtra("color")
        if (color == "ORANGE") {
            mainLayout.setBackgroundColor(resources.getColor(R.color.orange, null))
            titleCanti.setTextColor(resources.getColor(R.color.orange, null))
            titleValue.setTextColor(resources.getColor(R.color.orange, null))
            agregarButton.setBackgroundColor(resources.getColor(R.color.black, null))
            imageEye.setImageResource(R.drawable.pinkeye)
            modoEscalaGrises = false
        }else{
            mainLayout.setBackgroundColor(resources.getColor(R.color.darkgrey, null))
            titleCanti.setTextColor(resources.getColor(R.color.greytext, null))
            titleValue.setTextColor(resources.getColor(R.color.greytext, null))
            agregarButton.setBackgroundColor(resources.getColor(R.color.greytext, null))
            imageEye.setImageResource(R.drawable.blackeye)
            modoEscalaGrises = true
        }


        imageEye.setOnClickListener {
            modoEscalaGrises = !modoEscalaGrises
            if (modoEscalaGrises == true) {
                mainLayout.setBackgroundColor(resources.getColor(R.color.darkgrey, null))
                titleCanti.setTextColor(resources.getColor(R.color.greytext, null))
                titleValue.setTextColor(resources.getColor(R.color.greytext, null))
                agregarButton.setBackgroundColor(resources.getColor(R.color.greytext, null))
                imageEye.setImageResource(R.drawable.blackeye)
                color = "GREY"
                Toast.makeText(this, "Se ha activado una ayuda visual", Toast.LENGTH_SHORT).show()
            } else {
                mainLayout.setBackgroundColor(resources.getColor(R.color.orange, null))
                titleCanti.setTextColor(resources.getColor(R.color.orange, null))
                titleValue.setTextColor(resources.getColor(R.color.orange, null))
                agregarButton.setBackgroundColor(resources.getColor(R.color.black, null))
                imageEye.setImageResource(R.drawable.pinkeye)
                color = "ORANGE"
            }
        }

        idUsuario = intent.getStringExtra("id_usuario") ?: " "
        val productoId = intent.getIntExtra("producto_id", -1)
        val productoNombre = intent.getStringExtra("producto_nombre")
        productoPrecio = intent.getDoubleExtra("producto_precio", 0.0)
        val productoDescripcion = intent.getStringExtra("producto_descripcion")
        val productoImagen = intent.getStringExtra("producto_imagen")

        val imageProducto: ImageView = findViewById(R.id.imageProducto)
        val nombreProducto: TextView = findViewById(R.id.textNombreProducto)
        val precioProducto: TextView = findViewById(R.id.textPrecioProducto)
        val descripcionProducto: TextView = findViewById(R.id.textDescripcionProducto)
        
        nombreProducto.text = productoNombre
        precioProducto.text = "$${productoPrecio}"
        descripcionProducto.text = productoDescripcion

        Glide.with(this)
            .load(productoImagen)
            .placeholder(R.drawable.errorphotopeque)
            .error(R.drawable.errorphotopeque)
            .into(imageProducto)

        cargarStockDesdeApi(productoId)

        editCantidad.addTextChangedListener(object: android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //Caso que no requiere implementacion
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val cantidad= s.toString().toIntOrNull() ?: 0
                val total = cantidad * productoPrecio
                editValor.setText("$%.2f".format(total))
            }
            override fun afterTextChanged(s: android.text.Editable?){
                //Caso que no requiere implementacion
            }
        })



        agregarButton.setOnClickListener {
            val cantidadText = editCantidad.text.toString().trim()

            if (cantidadText.isEmpty() || cantidadText == "0") {
                Toast.makeText(this, "Por favor ingrese una cantidad válida", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val cantidad = cantidadText.toInt()

            if (cantidad > stockDisponible) {
                Toast.makeText(this, "La cantidad ingresada excede el stock disponible ($stockDisponible)", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val productoRequest = ProductoCarrito(
                idProducto = productoId,
                cantidad = cantidad,
                idUsuario = idUsuario,
                precioUnitario = productoPrecio
            )

            val client = OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor(this))
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://servicio-pedidos-596275467600.us-central1.run.app/api/")
                .client(client)

                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val api = retrofit.create(ApiService::class.java)
            Log.i("DETALLE", Gson().toJson(productoRequest))
            api.agregarDetallePedido(productoRequest).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@DetalleProductoActivity, "Producto agregado al carrito", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@DetalleProductoActivity, VerPedidoActivity::class.java).apply {
                            putExtra("id_usuario", idUsuario)
                            putExtra("color", color)
                        }
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@DetalleProductoActivity, "Error al agregar producto", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@DetalleProductoActivity, "Fallo la conexión del carrito", Toast.LENGTH_SHORT).show()
                }
            })

            }



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun cargarStockDesdeApi(productoId: Int) {
        if (productoId == null) {
            Toast.makeText(this, "ID del producto no disponible", Toast.LENGTH_SHORT).show()
            return
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(this))
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://inventarios-596275467600.us-central1.run.app/api/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        apiService.getStockProducto(productoId).enqueue(object : Callback<RespuestaInventario> {
            override fun onResponse(call: Call<RespuestaInventario>, response: Response<RespuestaInventario>) {
                if (response.isSuccessful) {
                    val inventario = response.body()?.inventario
                    val cantidadStock = inventario?.cantidadStock ?: 0
                    stockDisponible = cantidadStock
                    findViewById<TextView>(R.id.textStock).text = "Stock: $cantidadStock unidades"

                    if (cantidadStock <= 0) {
                        findViewById<Button>(R.id.buttonAgregar).isEnabled = false
                        findViewById<Button>(R.id.buttonAgregar).alpha = 0.5f
                        findViewById<EditText>(R.id.editCantidad).isEnabled = false
                    }

                } else {
                    findViewById<TextView>(R.id.textStock).text = "Stock: 0 unidades, producto no disponible"
                    findViewById<Button>(R.id.buttonAgregar).isEnabled = false
                    findViewById<Button>(R.id.buttonAgregar).alpha = 0.5f
                    findViewById<EditText>(R.id.editCantidad).isEnabled = false
                    //Toast.makeText(this@DetalleProductoActivity, "Error al cargar stock", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<RespuestaInventario>, t: Throwable) {
                findViewById<Button>(R.id.buttonAgregar).isEnabled = false
                findViewById<Button>(R.id.buttonAgregar).alpha = 0.5f
                findViewById<EditText>(R.id.editCantidad).isEnabled = false
                Toast.makeText(this@DetalleProductoActivity, "Error de conexión con inventario", Toast.LENGTH_SHORT).show()
            }
        })
    }
}