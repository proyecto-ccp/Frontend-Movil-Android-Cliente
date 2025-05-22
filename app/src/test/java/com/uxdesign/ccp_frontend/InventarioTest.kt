package com.uxdesign.ccp_frontend

import org.junit.Assert.*
import org.junit.Test

class InventarioTest {

    @Test
    fun crearInventarioCorrectamente() {
        val inventario = Inventario(
            id = "inv001",
            idProducto = 1001,
            cantidadStock = 50,
            fechaCreacion = "2025-05-22",
            fechaModificacion = "2025-05-23"
        )

        assertEquals("inv001", inventario.id)
        assertEquals(1001, inventario.idProducto)
        assertEquals(50, inventario.cantidadStock)
        assertEquals("2025-05-22", inventario.fechaCreacion)
        assertEquals("2025-05-23", inventario.fechaModificacion)
    }

    @Test
    fun inventariosConMismosValoresSonIguales() {
        val i1 = Inventario("inv002", 1002, 30, "2025-01-01", "2025-01-02")
        val i2 = Inventario("inv002", 1002, 30, "2025-01-01", "2025-01-02")

        assertEquals(i1, i2)
    }

    @Test
    fun copyModificaCantidadStock() {
        val original = Inventario("inv003", 1003, 10, "2025-03-01", "2025-03-02")
        val copia = original.copy(cantidadStock = 20)

        assertEquals(20, copia.cantidadStock)
        assertEquals(original.id, copia.id)
        assertEquals(original.idProducto, copia.idProducto)
    }

    @Test
    fun toStringContieneValoresEsperados() {
        val inventario = Inventario("inv004", 1004, 5, "2025-04-10", "2025-04-11")
        val str = inventario.toString()

        assertTrue(str.contains("id=inv004"))
        assertTrue(str.contains("idProducto=1004"))
        assertTrue(str.contains("cantidadStock=5"))
        assertTrue(str.contains("fechaCreacion=2025-04-10"))
        assertTrue(str.contains("fechaModificacion=2025-04-11"))
    }
}
