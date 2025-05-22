package com.uxdesign.ccp_frontend

import org.junit.Assert.*
import org.junit.Test

class ProductoCarritoDetalleTest {

    @Test
    fun crearProductoCarritoDetalleCorrectamente() {
        val detalle = ProductoCarritoDetalle(
            idPedido = "pedido123",
            idUsuario = "usuario456",
            idProducto = 789,
            cantidad = 2,
            precioUnitario = 99.99,
            nombreProducto = "Auriculares",
            urlFotoProducto1 = "http://fotos.com/auriculares1.jpg",
            urlFotoProducto2 = "http://fotos.com/auriculares2.jpg"
        )

        assertEquals("pedido123", detalle.idPedido)
        assertEquals("usuario456", detalle.idUsuario)
        assertEquals(789, detalle.idProducto)
        assertEquals(2, detalle.cantidad)
        assertEquals(99.99, detalle.precioUnitario, 0.0)
        assertEquals("Auriculares", detalle.nombreProducto)
        assertEquals("http://fotos.com/auriculares1.jpg", detalle.urlFotoProducto1)
        assertEquals("http://fotos.com/auriculares2.jpg", detalle.urlFotoProducto2)
    }

    @Test
    fun detallesConMismosValoresSonIguales() {
        val d1 = ProductoCarritoDetalle(
            "pedido456", "usuario789", 321, 1, 49.95,
            "Mouse", "http://fotos.com/mouse1.jpg", "http://fotos.com/mouse2.jpg"
        )
        val d2 = ProductoCarritoDetalle(
            "pedido456", "usuario789", 321, 1, 49.95,
            "Mouse", "http://fotos.com/mouse1.jpg", "http://fotos.com/mouse2.jpg"
        )

        assertEquals(d1, d2)
    }

    @Test
    fun copyModificaCantidad() {
        val original = ProductoCarritoDetalle(
            "pedido789", "usuario101", 654, 3, 75.00,
            "Teclado", "http://fotos.com/teclado1.jpg", "http://fotos.com/teclado2.jpg"
        )
        val copia = original.copy(cantidad = 5)

        assertEquals(5, copia.cantidad)
        assertEquals(original.idPedido, copia.idPedido)
        assertEquals(original.nombreProducto, copia.nombreProducto)
    }

    @Test
    fun toStringContieneValoresEsperados() {
        val detalle = ProductoCarritoDetalle(
            "pedido999", "usuario999", 987, 4, 150.00,
            "Monitor", "http://fotos.com/monitor1.jpg", "http://fotos.com/monitor2.jpg"
        )
        val str = detalle.toString()

        assertTrue(str.contains("idPedido=pedido999"))
        assertTrue(str.contains("idUsuario=usuario999"))
        assertTrue(str.contains("idProducto=987"))
        assertTrue(str.contains("cantidad=4"))
        assertTrue(str.contains("precioUnitario=150.0"))
        assertTrue(str.contains("nombreProducto=Monitor"))
        assertTrue(str.contains("urlFotoProducto1=http://fotos.com/monitor1.jpg"))
        assertTrue(str.contains("urlFotoProducto2=http://fotos.com/monitor2.jpg"))
    }
}
