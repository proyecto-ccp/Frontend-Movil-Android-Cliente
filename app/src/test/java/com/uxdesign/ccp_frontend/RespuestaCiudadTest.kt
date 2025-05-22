package com.uxdesign.ccp_frontend

import org.junit.Assert.*
import org.junit.Test

class RespuestaCiudadTest {

    @Test
    fun crearRespuestaCiudadCorrectamente() {
        val ciudad1 = Ciudad("c001", "Bogotá", 8000000)
        val ciudad2 = Ciudad("c002", "Medellín", 2500000)

        val respuesta = RespuestaCiudad(
            resultado = 1,
            mensaje = "Consulta exitosa",
            status = 200,
            ciudades = listOf(ciudad1, ciudad2)
        )

        assertEquals(1, respuesta.resultado)
        assertEquals("Consulta exitosa", respuesta.mensaje)
        assertEquals(200, respuesta.status)
        assertNotNull(respuesta.ciudades)
        assertEquals(2, respuesta.ciudades.size)

        assertEquals("c001", respuesta.ciudades[0].id)
        assertEquals("Bogotá", respuesta.ciudades[0].nombre)
        assertEquals(8000000, respuesta.ciudades[0].poblacion)

        assertEquals("c002", respuesta.ciudades[1].id)
        assertEquals("Medellín", respuesta.ciudades[1].nombre)
        assertEquals(2500000, respuesta.ciudades[1].poblacion)
    }

    @Test
    fun respuestasConMismosValoresSonIguales() {
        val ciudadList = listOf(
            Ciudad("c003", "Cali", 2200000),
            Ciudad("c004", "Barranquilla", 1500000)
        )

        val r1 = RespuestaCiudad(0, "OK", 100, ciudadList)
        val r2 = RespuestaCiudad(0, "OK", 100, ciudadList)

        assertEquals(r1, r2)
    }

    @Test
    fun copyModificaMensaje() {
        val original = RespuestaCiudad(1, "Mensaje original", 200, emptyList())
        val copia = original.copy(mensaje = "Mensaje modificado")

        assertEquals("Mensaje modificado", copia.mensaje)
        assertEquals(original.resultado, copia.resultado)
        assertEquals(original.status, copia.status)
    }

    @Test
    fun toStringContieneValoresEsperados() {
        val respuesta = RespuestaCiudad(
            2, "Todo bien", 201,
            listOf(Ciudad("c005", "Cartagena", 1000000))
        )
        val str = respuesta.toString()

        assertTrue(str.contains("resultado=2"))
        assertTrue(str.contains("mensaje=Todo bien"))
        assertTrue(str.contains("status=201"))
        assertTrue(str.contains("ciudades=[Ciudad"))
    }
}
