package com.uxdesign.ccp_frontend.helpers

import android.content.Context
import android.util.Log
import com.uxdesign.ccp_frontend.ApiService
import com.uxdesign.ccp_frontend.AuthInterceptor
import com.uxdesign.ccp_frontend.Producto
import com.uxdesign.ccp_frontend.ProductoCache
import com.uxdesign.ccp_frontend.guardarProductosEnCache
import com.uxdesign.ccp_frontend.isCacheExpirada
import com.uxdesign.ccp_frontend.isInternetDisponible
import com.uxdesign.ccp_frontend.leerProductosDesdeCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CatalogoRepository {

    suspend fun obtenerProductos(context: Context): List<Producto> {
        val maxTiempoCache = 60 * 60 * 3000L // 3 horas

        ProductoCache.productos?.let {
            return it
        }

        if (!isCacheExpirada(context, maxTiempoCache)) {
            leerProductosDesdeCache(context)?.let {
                ProductoCache.productos = it
                return it
            }
        }

        if (isInternetDisponible(context)) {
            Log.i("Internet", "si tiene")
            val productos = fetchDesdeApi(context)
            guardarProductosEnCache(context, productos)
            ProductoCache.productos = productos
            return productos
        } else {
            Log.i("Internet", "no tiene")
            return leerProductosDesdeCache(context)
                ?.also { ProductoCache.productos = it }
                ?: throw Exception("No hay conexión y no hay caché disponible")
        }
    }

    private suspend fun fetchDesdeApi(context: Context): List<Producto> {
        return withContext(Dispatchers.IO) {
            val client = OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor(context))
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://productos-596275467600.us-central1.run.app/api/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiService = retrofit.create(ApiService::class.java)
            val response = apiService.getProductos().execute()
            if (response.isSuccessful) {
                return@withContext response.body()?.productos ?: emptyList()
            } else {
                throw Exception("Error al obtener catálogo: ${response.code()}")
            }
        }
    }
}
