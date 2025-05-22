package com.uxdesign.ccp_frontend

import org.junit.Assert.*
import org.junit.Test

class ProductoCarritoTest {

    @Test
    fun crearProductoCarritoCorrectamente() {
        val productoCarrito = ProductoCarrito(
            idProducto = 501,
            cantidad = 3,
            idUsuario = "user123",
            precioUnitario = 25.50
        )

        assertEquals(501, productoCarrito.idProducto)
        assertEquals(3, productoCarrito.cantidad)
        assertEquals("user123", productoCarrito.idUsuario)
        assertEquals(25.50, productoCarrito.precioUnitario, 0.0)
    }

    @Test
    fun productosCarritoConMismosValoresSonIguales() {
        val p1 = ProductoCarrito(502, 1, "user456", 15.75)
        val p2 = ProductoCarrito(502, 1, "user456", 15.75)

        assertEquals(p1, p2)
    }

    @Test
    fun copyModificaCantidad() {
        val original = ProductoCarrito(503, 2, "user789", 30.0)
        val copia = original.copy(cantidad = 5)

        assertEquals(5, copia.cantidad)
        assertEquals(original.idProducto, copia.idProducto)
        assertEquals(original.idUsuario, copia.idUsuario)
    }

    @Test
    fun toStringContieneValoresEsperados() {
        val productoCarrito = ProductoCarrito(504, 4, "user999", 10.0)
        val str = productoCarrito.toString()

        assertTrue(str.contains("idProducto=504"))
        assertTrue(str.contains("cantidad=4"))
        assertTrue(str.contains("idUsuario=user999"))
        assertTrue(str.contains("precioUnitario=10.0"))
    }
}
