package com.uxdesign.ccp_frontend

data class ProductoCarritoDetalle(
    val idPedido: String,
    val idUsuario: String,
    val idProducto: Int,
    val cantidad: Int,
    val precioUnitario: Double,
    val nombreProducto: String,
    val urlFotoProducto1: String,
    val urlFotoProducto2: String

)
