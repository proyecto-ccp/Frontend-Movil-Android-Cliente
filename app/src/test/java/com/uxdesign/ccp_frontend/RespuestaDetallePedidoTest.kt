package com.uxdesign.ccp_frontend

import org.junit.Assert.*
import org.junit.Test

class RespuestaDetallePedidoTest {

    @Test
    fun crearRespuestaDetallePedidoCorrectamente() {
        val detalle1 = ProductoCarritoDetalle(
            idPedido = "pedido001",
            idUsuario = "usuario1",
            idProducto = 101,
            cantidad = 2,
            precioUnitario = 19.99,
            nombreProducto = "Producto A",
            urlFotoProducto1 = "http://foto1.com/a.jpg",
            urlFotoProducto2 = "http://foto2.com/a.jpg"
        )
        val detalle2 = ProductoCarritoDetalle(
            idPedido = "pedido002",
            idUsuario = "usuario2",
            idProducto = 102,
            cantidad = 1,
            precioUnitario = 29.99,
            nombreProducto = "Producto B",
            urlFotoProducto1 = "http://foto1.com/b.jpg",
            urlFotoProducto2 = "http://foto2.com/b.jpg"
        )

        val respuesta = RespuestaDetallePedido(
            resultado = 1,
            mensaje = "Consulta exitosa",
            status = 200,
            detallePedidos = listOf(detalle1, detalle2)
        )

        assertEquals(1, respuesta.resultado)
        assertEquals("Consulta exitosa", respuesta.mensaje)
        assertEquals(200, respuesta.status)
        assertNotNull(respuesta.detallePedidos)
        assertEquals(2, respuesta.detallePedidos.size)

        val d1 = respuesta.detallePedidos[0]
        assertEquals("pedido001", d1.idPedido)
        assertEquals("usuario1", d1.idUsuario)
        assertEquals(101, d1.idProducto)
        assertEquals(2, d1.cantidad)
        assertEquals(19.99, d1.precioUnitario, 0.0)
        assertEquals("Producto A", d1.nombreProducto)
        assertEquals("http://foto1.com/a.jpg", d1.urlFotoProducto1)
        assertEquals("http://foto2.com/a.jpg", d1.urlFotoProducto2)

        val d2 = respuesta.detallePedidos[1]
        assertEquals("pedido002", d2.idPedido)
        assertEquals("usuario2", d2.idUsuario)
        assertEquals(102, d2.idProducto)
        assertEquals(1, d2.cantidad)
        assertEquals(29.99, d2.precioUnitario, 0.0)
        assertEquals("Producto B", d2.nombreProducto)
        assertEquals("http://foto1.com/b.jpg", d2.urlFotoProducto1)
        assertEquals("http://foto2.com/b.jpg", d2.urlFotoProducto2)
    }

    @Test
    fun igualdadDeRespuestas() {
        val detalles = listOf(
            ProductoCarritoDetalle("pedido003", "usuario3", 103, 5, 9.99, "Producto C", "url1", "url2"),
            ProductoCarritoDetalle("pedido004", "usuario4", 104, 3, 14.99, "Producto D", "url3", "url4")
        )

        val r1 = RespuestaDetallePedido(0, "OK", 100, detalles)
        val r2 = RespuestaDetallePedido(0, "OK", 100, detalles)

        assertEquals(r1, r2)
    }

    @Test
    fun copiaModificaMensaje() {
        val original = RespuestaDetallePedido(1, "Mensaje original", 200, emptyList())
        val copia = original.copy(mensaje = "Mensaje modificado")

        assertEquals("Mensaje modificado", copia.mensaje)
        assertEquals(original.resultado, copia.resultado)
        assertEquals(original.status, copia.status)
    }

    @Test
    fun toStringContieneCampos() {
        val respuesta = RespuestaDetallePedido(
            2, "Todo bien", 201,
            listOf(ProductoCarritoDetalle("pedido005", "usuario5", 105, 4, 19.99, "Producto E", "url5", "url6"))
        )
        val str = respuesta.toString()

        assertTrue(str.contains("resultado=2"))
        assertTrue(str.contains("mensaje=Todo bien"))
        assertTrue(str.contains("status=201"))
        assertTrue(str.contains("detallePedidos=[ProductoCarritoDetalle"))
    }
}
