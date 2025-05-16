package com.uxdesign.ccp_frontend

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("Cliente/CrearCliente")
    fun registrarCliente(@Body cliente: Cliente): Call<ResponseBody>

    @GET("Atributos/TiposDocumento")
    fun getTiposDocumento(): Call<RespuestaTiposDocs>

    @GET("Productos/Consultar")
    fun getProductos(): Call<RespuestaProducto>

    @GET("atributos/Localizacion/Ciudades")
    fun getCiudades(): Call<RespuestaCiudad>

    @GET("Atributos/Localizacion/Ciudad/{IdCiudad}/Zonas")
    fun getZonasPorCiudad(@Path("IdCiudad") idCiudad: String): Call<RespuestaZona>

    @GET("Pedido/ObtenerPedidosPorCliente/{clienteId}/{estado}")
    fun getPedidosPorCliente(@Path("clienteId") clienteId: String, @Path("estado") estado: String
    ): Call<RespuestaPedidoProcesado>

    @POST("DetallePedido/AgregarDetalle")
    fun agregarDetallePedido(@Body detalle: ProductoCarrito): Call<Void>

    @GET("Inventarios/Consultar")
    fun getStockProducto(@Query("idProducto") idProducto: Int): Call<RespuestaInventario>

    @GET("DetallePedido/ObtenerDetallesUsuario/{id}")
    fun getDetallePedidoUsuario(@Path("id") clienteId: String): Call<RespuestaDetalleCarrito>

    @GET("DetallePedido/ObtenerDetalles/{id}")
    fun getDetallePedido(@Path("id") pedidoId: String): Call<RespuestaDetallePedido>

   @POST("Pedido/CrearPedido")
    fun crearPedido(@Body request: Pedido): Call<RespuestaRequestPedido>

    @PUT("DetallePedido/ActualizarDetalles/{idUsuario}/{idPedido}")
    fun enlazarDetallePedido(@Path("idUsuario") idUsuario: String,
        @Path("idPedido") idPedido: String): Call<RespuestaRequestPedido>

    @GET("Cliente/ObtenerCliente/{id}")
    fun getClienteId(@Path("id") clienteId: String): Call<SingleCliente>

    @POST("Usuarios/Login")
    fun login(@Body request: LoginRequest ): Call<RespuestaLogin>
}
