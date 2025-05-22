package com.uxdesign.ccp_frontend

import org.junit.Assert.*
import org.junit.Test

class RespuestaClienteTest {

    @Test
    fun crearRespuestaClienteCorrectamente() {
        val cliente1 = Cliente(
            id = "cli001",
            nombre = "Juan",
            apellido = "Pérez",
            documento = "12345678",
            tipoDocumento = "CC",
            telefono = "3001234567",
            email = "juan.perez@mail.com",
            direccion = "Calle 123",
            ciudad = "Bogotá",
            idZona = "z001",
            zona = "Zona Norte",
            contrasenia = "pass123"
        )

        val cliente2 = Cliente(
            id = "cli002",
            nombre = "Ana",
            apellido = "Gómez",
            documento = "87654321",
            tipoDocumento = "CC",
            telefono = "3007654321",
            email = "ana.gomez@mail.com",
            direccion = "Carrera 45",
            ciudad = "Medellín",
            idZona = "z002",
            zona = "Zona Sur",
            contrasenia = "pass456"
        )

        val respuesta = RespuestaCliente(
            resultado = 1,
            mensaje = "Consulta exitosa",
            status = 200,
            clientes = listOf(cliente1, cliente2)
        )

        assertEquals(1, respuesta.resultado)
        assertEquals("Consulta exitosa", respuesta.mensaje)
        assertEquals(200, respuesta.status)
        assertNotNull(respuesta.clientes)
        assertEquals(2, respuesta.clientes.size)

        assertEquals("cli001", respuesta.clientes[0].id)
        assertEquals("Juan", respuesta.clientes[0].nombre)
        assertEquals("Pérez", respuesta.clientes[0].apellido)

        assertEquals("cli002", respuesta.clientes[1].id)
        assertEquals("Ana", respuesta.clientes[1].nombre)
        assertEquals("Gómez", respuesta.clientes[1].apellido)
    }

    @Test
    fun respuestasConMismosValoresSonIguales() {
        val clientesList = listOf(
            Cliente("cli003", "Luis", "Martínez", "11223344", "CC", "3001122334", "luis@mail.com", "Calle 44", "Cali", "z003", "Zona Este", "pass789"),
            Cliente("cli004", "Marta", "Lopez", "44332211", "CC", "3004433221", "marta@mail.com", "Carrera 22", "Barranquilla", "z004", "Zona Oeste", "pass321")
        )

        val r1 = RespuestaCliente(0, "OK", 100, clientesList)
        val r2 = RespuestaCliente(0, "OK", 100, clientesList)

        assertEquals(r1, r2)
    }

    @Test
    fun copyModificaMensaje() {
        val original = RespuestaCliente(1, "Mensaje original", 200, emptyList())
        val copia = original.copy(mensaje = "Mensaje modificado")

        assertEquals("Mensaje modificado", copia.mensaje)
        assertEquals(original.resultado, copia.resultado)
        assertEquals(original.status, copia.status)
    }

    @Test
    fun toStringContieneValoresEsperados() {
        val respuesta = RespuestaCliente(
            2, "Todo bien", 201,
            listOf(Cliente("cli005", "Sofia", "Ramirez", "55667788", "CC", "3005566778", "sofia@mail.com", "Calle 55", "Cartagena", "z005", "Zona Centro", "pass999"))
        )
        val str = respuesta.toString()

        assertTrue(str.contains("resultado=2"))
        assertTrue(str.contains("mensaje=Todo bien"))
        assertTrue(str.contains("status=201"))
        assertTrue(str.contains("clientes=[Cliente"))
    }
}
