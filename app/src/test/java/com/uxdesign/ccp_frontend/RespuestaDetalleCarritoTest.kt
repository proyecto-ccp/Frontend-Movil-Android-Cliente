package com.uxdesign.ccp_frontend

import org.junit.Assert.*
import org.junit.Test

class RespuestaDetalleCarritoTest {

    @Test
    fun crearRespuestaDetalleCarritoCorrectamente() {
        val producto1 = ProductoCarrito(
            idProducto = 1,
            cantidad = 3,
            idUsuario = "user1",
            precioUnitario = 10.0
        )
        val producto2 = ProductoCarrito(
            idProducto = 2,
            cantidad = 5,
            idUsuario = "user1",
            precioUnitario = 20.0
        )

        val respuesta = RespuestaDetalleCarrito(
            resultado = 0,
            mensaje = "Éxito",
            status = 200,
            detallePedidos = listOf(producto1, producto2)
        )

        assertEquals(0, respuesta.resultado)
        assertEquals("Éxito", respuesta.mensaje)
        assertEquals(200, respuesta.status)
        assertNotNull(respuesta.detallePedidos)
        assertEquals(2, respuesta.detallePedidos.size)

        assertEquals(1, respuesta.detallePedidos[0].idProducto)
        assertEquals(3, respuesta.detallePedidos[0].cantidad)
        assertEquals("user1", respuesta.detallePedidos[0].idUsuario)
        assertEquals(10.0, respuesta.detallePedidos[0].precioUnitario, 0.0)

        assertEquals(2, respuesta.detallePedidos[1].idProducto)
        assertEquals(5, respuesta.detallePedidos[1].cantidad)
        assertEquals("user1", respuesta.detallePedidos[1].idUsuario)
        assertEquals(20.0, respuesta.detallePedidos[1].precioUnitario, 0.0)
    }

    @Test
    fun igualdadDeRespuestas() {
        val productos = listOf(
            ProductoCarrito(3, 1, "user2", 15.0),
            ProductoCarrito(4, 4, "user2", 25.0)
        )

        val r1 = RespuestaDetalleCarrito(1, "OK", 100, productos)
        val r2 = RespuestaDetalleCarrito(1, "OK", 100, productos)

        assertEquals(r1, r2)
    }

    @Test
    fun copiaModificaMensaje() {
        val original = RespuestaDetalleCarrito(1, "Original", 200, emptyList())
        val copia = original.copy(mensaje = "Modificado")

        assertEquals("Modificado", copia.mensaje)
        assertEquals(original.resultado, copia.resultado)
        assertEquals(original.status, copia.status)
    }

    @Test
    fun toStringContieneCampos() {
        val respuesta = RespuestaDetalleCarrito(
            2, "Todo bien", 201,
            listOf(ProductoCarrito(5, 2, "user3", 30.0))
        )
        val str = respuesta.toString()

        assertTrue(str.contains("resultado=2"))
        assertTrue(str.contains("mensaje=Todo bien"))
        assertTrue(str.contains("status=201"))
        assertTrue(str.contains("detallePedidos=[ProductoCarrito"))
    }
}
