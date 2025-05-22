package com.uxdesign.ccp_frontend

import org.junit.Assert.*
import org.junit.Test

class RespuestaInventarioTest {

    @Test
    fun crearRespuestaInventarioCorrectamente() {
        val inventario = Inventario(
            id = "inv001",
            idProducto = 1001,
            cantidadStock = 50,
            fechaCreacion = "2024-01-01",
            fechaModificacion = "2024-05-01"
        )

        val respuesta = RespuestaInventario(
            resultado = 1,
            mensaje = "Inventario obtenido con éxito",
            status = 200,
            inventario = inventario
        )

        assertEquals(1, respuesta.resultado)
        assertEquals("Inventario obtenido con éxito", respuesta.mensaje)
        assertEquals(200, respuesta.status)

        val inv = respuesta.inventario
        assertEquals("inv001", inv.id)
        assertEquals(1001, inv.idProducto)
        assertEquals(50, inv.cantidadStock)
        assertEquals("2024-01-01", inv.fechaCreacion)
        assertEquals("2024-05-01", inv.fechaModificacion)
    }

    @Test
    fun respuestasConMismosValoresSonIguales() {
        val inv = Inventario("inv002", 1002, 75, "2024-02-02", "2024-05-02")
        val r1 = RespuestaInventario(0, "OK", 100, inv)
        val r2 = RespuestaInventario(0, "OK", 100, inv)

        assertEquals(r1, r2)
    }

    @Test
    fun copiaModificaMensaje() {
        val original = RespuestaInventario(
            resultado = 1,
            mensaje = "Mensaje original",
            status = 200,
            inventario = Inventario("inv003", 1003, 100, "2024-01-01", "2024-05-05")
        )
        val copia = original.copy(mensaje = "Mensaje modificado")

        assertEquals("Mensaje modificado", copia.mensaje)
        assertEquals(original.resultado, copia.resultado)
        assertEquals(original.status, copia.status)
        assertEquals(original.inventario, copia.inventario)
    }

    @Test
    fun toStringContieneCamposEsperados() {
        val respuesta = RespuestaInventario(
            2,
            "Consulta completa",
            201,
            Inventario("inv004", 1004, 30, "2024-03-01", "2024-05-10")
        )

        val str = respuesta.toString()
        assertTrue(str.contains("resultado=2"))
        assertTrue(str.contains("mensaje=Consulta completa"))
        assertTrue(str.contains("status=201"))
        assertTrue(str.contains("inventario=Inventario"))
    }
}
