package com.uxdesign.ccp_frontend

data class RespuestaDetallePedido(
    val resultado: Int,
    val mensaje: String,
    val status: Int,
    val detallePedidos: List<ProductoCarritoDetalle>
)
