package com.uxdesign.ccp_frontend

import org.junit.Assert.*
import org.junit.Test

class RespuestaRequestPedidoTest {

    @Test
    fun crearRespuestaRequestPedidoCorrectamente() {
        val respuesta = RespuestaRequestPedido(
            id = "pedido123",
            resultado = 1,
            mensaje = "Pedido registrado con éxito",
            status = 201
        )

        assertEquals("pedido123", respuesta.id)
        assertEquals(1, respuesta.resultado)
        assertEquals("Pedido registrado con éxito", respuesta.mensaje)
        assertEquals(201, respuesta.status)
    }

    @Test
    fun respuestasConMismosDatosSonIguales() {
        val r1 = RespuestaRequestPedido("id001", 1, "OK", 200)
        val r2 = RespuestaRequestPedido("id001", 1, "OK", 200)

        assertEquals(r1, r2)
    }

    @Test
    fun copiaModificaMensajeYStatus() {
        val original = RespuestaRequestPedido(
            id = "id002",
            resultado = 0,
            mensaje = "Error al registrar pedido",
            status = 500
        )

        val copia = original.copy(
            mensaje = "Reintentar operación",
            status = 400
        )

        assertEquals("id002", copia.id)
        assertEquals(0, copia.resultado)
        assertEquals("Reintentar operación", copia.mensaje)
        assertEquals(400, copia.status)
    }

    @Test
    fun toStringIncluyeCamposEsperados() {
        val respuesta = RespuestaRequestPedido("abc123", 1, "Registrado", 200)
        val str = respuesta.toString()

        assertTrue(str.contains("id=abc123"))
        assertTrue(str.contains("resultado=1"))
        assertTrue(str.contains("mensaje=Registrado"))
        assertTrue(str.contains("status=200"))
    }
}
