package com.uxdesign.ccp_frontend

import org.junit.Assert.*
import org.junit.Test

class RespuestaVendedorTest {

    // Helper para crear un vendedor de prueba
    private fun crearVendedorPrueba(): Vendedor {
        return Vendedor(
            id = "v001",
            idTipoDocumento = 1,
            numeroDocumento = "12345678",
            nombre = "Juan",
            apellido = "Pérez",
            telefono = "123456789",
            correo = "juan.perez@example.com",
            direccion = "Calle Falsa 123",
            idzona = "z001"
        )
    }

    @Test
    fun crearRespuestaVendedorCorrectamente() {
        val vendedor = crearVendedorPrueba()
        val respuesta = RespuestaVendedor(
            resultado = 1,
            mensaje = "Operación exitosa",
            status = 200,
            vendedor = vendedor
        )

        assertEquals(1, respuesta.resultado)
        assertEquals("Operación exitosa", respuesta.mensaje)
        assertEquals(200, respuesta.status)
        assertEquals(vendedor, respuesta.vendedor)
    }

    @Test
    fun dosRespuestasVendedorConMismosValoresSonIguales() {
        val vendedor1 = crearVendedorPrueba()
        val vendedor2 = crearVendedorPrueba()

        val r1 = RespuestaVendedor(0, "Error", 400, vendedor1)
        val r2 = RespuestaVendedor(0, "Error", 400, vendedor2)

        assertEquals(r1, r2)
    }

    @Test
    fun copyModificaMensaje() {
        val original = RespuestaVendedor(1, "Pendiente", 102, crearVendedorPrueba())
        val copia = original.copy(mensaje = "Completado")

        assertEquals("Completado", copia.mensaje)
        assertEquals(1, copia.resultado)
        assertEquals(crearVendedorPrueba(), copia.vendedor)
    }

    @Test
    fun toStringContieneValoresEsperados() {
        val respuesta = RespuestaVendedor(1, "OK", 200, crearVendedorPrueba())
        val str = respuesta.toString()

        assertTrue(str.contains("resultado=1"))
        assertTrue(str.contains("mensaje=OK"))
        assertTrue(str.contains("status=200"))
        assertTrue(str.contains("vendedor=")) // revisa que esté el toString del vendedor
    }
}

