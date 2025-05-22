package com.uxdesign.ccp_frontend

import org.junit.Assert.*
import org.junit.Test

class RespuestaZonaTest {

    // Helper para crear zonas de prueba
    private fun crearZonasPrueba(): List<Zona> {
        return listOf(
            Zona("z001", "c001", "Madrid", "Centro", "Norte-Sur-Este-Oeste"),
            Zona("z002", "c002", "Barcelona", "Eixample", "N-S-E-O")
        )
    }

    @Test
    fun crearRespuestaZonaCorrectamente() {
        val zonas = crearZonasPrueba()
        val respuesta = RespuestaZona(
            resultado = 1,
            mensaje = "Consulta exitosa",
            status = 200,
            zonas = zonas
        )

        assertEquals(1, respuesta.resultado)
        assertEquals("Consulta exitosa", respuesta.mensaje)
        assertEquals(200, respuesta.status)
        assertEquals(zonas, respuesta.zonas)
        assertEquals(2, respuesta.zonas.size)
        assertEquals("Madrid", respuesta.zonas[0].ciudad)
    }

    @Test
    fun dosRespuestasZonaConMismosValoresSonIguales() {
        val zonas1 = crearZonasPrueba()
        val zonas2 = crearZonasPrueba()

        val r1 = RespuestaZona(0, "Error", 400, zonas1)
        val r2 = RespuestaZona(0, "Error", 400, zonas2)

        assertEquals(r1, r2)
    }

    @Test
    fun copyModificaMensaje() {
        val original = RespuestaZona(1, "Pendiente", 102, crearZonasPrueba())
        val copia = original.copy(mensaje = "Completado")

        assertEquals("Completado", copia.mensaje)
        assertEquals(1, copia.resultado)
        assertEquals(2, copia.zonas.size)
    }

    @Test
    fun toStringContieneValoresEsperados() {
        val respuesta = RespuestaZona(1, "OK", 200, crearZonasPrueba())
        val str = respuesta.toString()

        assertTrue(str.contains("resultado=1"))
        assertTrue(str.contains("mensaje=OK"))
        assertTrue(str.contains("status=200"))
        assertTrue(str.contains("zonas="))
        assertTrue(str.contains("Madrid"))
        assertTrue(str.contains("Barcelona"))
    }
}
