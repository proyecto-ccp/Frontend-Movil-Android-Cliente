package com.uxdesign.ccp_frontend

import org.junit.Assert.*
import org.junit.Test

class SingleClienteTest {

    // Helper para crear un Cliente de prueba
    private fun crearClientePrueba(): Cliente {
        return Cliente(
            id = "cl001",
            nombre = "Carlos",
            apellido = "Ramirez",
            documento = "12345678",
            tipoDocumento = "DNI",
            telefono = "987654321",
            email = "carlos@example.com",
            direccion = "Calle Falsa 123",
            ciudad = "Madrid",
            idZona = "z001",
            zona = "Centro",
            contrasenia = "pass123"
        )
    }

    @Test
    fun crearSingleClienteCorrectamente() {
        val cliente = crearClientePrueba()
        val singleCliente = SingleCliente(
            cliente = cliente,
            resultado = 1,
            mensaje = "Éxito",
            status = 200
        )

        assertEquals(cliente, singleCliente.cliente)
        assertEquals(1, singleCliente.resultado)
        assertEquals("Éxito", singleCliente.mensaje)
        assertEquals(200, singleCliente.status)
    }

    @Test
    fun dosSingleClientesConMismosValoresSonIguales() {
        val cliente = crearClientePrueba()

        val sc1 = SingleCliente(cliente, 0, "Error", 400)
        val sc2 = SingleCliente(cliente, 0, "Error", 400)

        assertEquals(sc1, sc2)
    }

    @Test
    fun copyModificaMensaje() {
        val cliente = crearClientePrueba()
        val original = SingleCliente(cliente, 1, "Pendiente", 102)
        val copia = original.copy(mensaje = "Completado")

        assertEquals("Completado", copia.mensaje)
        assertEquals(1, copia.resultado)
        assertEquals(cliente, copia.cliente)
    }

    @Test
    fun toStringContieneValoresEsperados() {
        val cliente = crearClientePrueba()
        val singleCliente = SingleCliente(cliente, 1, "OK", 200)
        val str = singleCliente.toString()

        assertTrue(str.contains("resultado=1"))
        assertTrue(str.contains("mensaje=OK"))
        assertTrue(str.contains("status=200"))
        assertTrue(str.contains("cliente=")) // revisa que esté el toString del cliente
    }
}
