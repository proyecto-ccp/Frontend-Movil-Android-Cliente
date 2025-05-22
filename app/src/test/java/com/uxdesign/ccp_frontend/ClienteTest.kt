package com.uxdesign.ccp_frontend

import org.junit.Assert.*
import org.junit.Test

class ClienteTest {

    @Test
    fun crearClienteCorrectamente() {
        val cliente = Cliente(
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

        assertEquals("cl001", cliente.id)
        assertEquals("Carlos", cliente.nombre)
        assertEquals("Ramirez", cliente.apellido)
        assertEquals("12345678", cliente.documento)
        assertEquals("DNI", cliente.tipoDocumento)
        assertEquals("987654321", cliente.telefono)
        assertEquals("carlos@example.com", cliente.email)
        assertEquals("Calle Falsa 123", cliente.direccion)
        assertEquals("Madrid", cliente.ciudad)
        assertEquals("z001", cliente.idZona)
        assertEquals("Centro", cliente.zona)
        assertEquals("pass123", cliente.contrasenia)
    }

    @Test
    fun clientesConMismosValoresSonIguales() {
        val c1 = Cliente(
            "cl002", "Ana", "Lopez", "87654321", "DNI",
            "123456789", "ana@example.com", "Av. Siempre Viva 742",
            "Barcelona", "z002", "Eixample", "pass456"
        )
        val c2 = Cliente(
            "cl002", "Ana", "Lopez", "87654321", "DNI",
            "123456789", "ana@example.com", "Av. Siempre Viva 742",
            "Barcelona", "z002", "Eixample", "pass456"
        )

        assertEquals(c1, c2)
    }

    @Test
    fun copyModificaEmail() {
        val original = Cliente(
            "cl003", "Luis", "Martínez", "11223344", "Pasaporte",
            "111222333", "luis@example.com", "Calle 10", "Valencia",
            "z003", "Norte", "secret"
        )
        val copia = original.copy(email = "nuevo@email.com")

        assertEquals("nuevo@email.com", copia.email)
        assertEquals(original.id, copia.id)
        assertEquals(original.nombre, copia.nombre)
    }

    @Test
    fun toStringContieneValoresEsperados() {
        val cliente = Cliente(
            "cl004", "Carla", "Gómez", "99887766", "Carnet",
            "222333444", "carla@example.com", "Calle Luna", "Sevilla",
            "z004", "Casco Antiguo", "clave123"
        )
        val str = cliente.toString()

        assertTrue(str.contains("id=cl004"))
        assertTrue(str.contains("nombre=Carla"))
        assertTrue(str.contains("apellido=Gómez"))
        assertTrue(str.contains("documento=99887766"))
        assertTrue(str.contains("tipoDocumento=Carnet"))
        assertTrue(str.contains("telefono=222333444"))
        assertTrue(str.contains("email=carla@example.com"))
        assertTrue(str.contains("direccion=Calle Luna"))
        assertTrue(str.contains("ciudad=Sevilla"))
        assertTrue(str.contains("idZona=z004"))
        assertTrue(str.contains("zona=Casco Antiguo"))
        assertTrue(str.contains("contrasenia=clave123"))
    }
}
