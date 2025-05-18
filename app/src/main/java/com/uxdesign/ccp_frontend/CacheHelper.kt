package com.uxdesign.ccp_frontend

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

private const val PRODUCT_CACHE_FILE = "productos_cache.json"
private const val PREFS_NAME = "mi_cache_prefs"
private const val CACHE_TIMESTAMP_KEY = "productos_cache_timestamp"

fun guardarProductosEnCache(context: Context, productos: List<Producto>) {
    val gson = Gson()
    val jsonString = gson.toJson(productos)
    val file = File(context.cacheDir, PRODUCT_CACHE_FILE)
    file.writeText(jsonString)

    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    prefs.edit().putLong(CACHE_TIMESTAMP_KEY, System.currentTimeMillis()).apply()
}

fun leerProductosDesdeCache(context: Context): List<Producto>? {
    val file = File(context.cacheDir, PRODUCT_CACHE_FILE)
    if (!file.exists()) return null

    val jsonString = file.readText()
    val gson = Gson()
    val listType = object : TypeToken<List<Producto>>() {}.type
    return gson.fromJson(jsonString, listType)
}

fun isCacheExpirada(context: Context, maxTiempoMillis: Long): Boolean {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val ultimoGuardado = prefs.getLong(CACHE_TIMESTAMP_KEY, 0L)
    val ahora = System.currentTimeMillis()
    return (ahora - ultimoGuardado) > maxTiempoMillis
}

fun isInternetDisponible(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    } else {
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}