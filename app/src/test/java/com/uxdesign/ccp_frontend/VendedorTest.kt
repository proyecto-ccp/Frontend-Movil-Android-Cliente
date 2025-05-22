package com.uxdesign.ccp_frontend

import org.junit.Assert.*
import org.junit.Test

class VendedorTest {

    @Test
    fun crearVendedorCorrectamente() {
        val vendedor = Vendedor(
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

        assertEquals("v001", vendedor.id)
        assertEquals(1, vendedor.idTipoDocumento)
        assertEquals("12345678", vendedor.numeroDocumento)
        assertEquals("Juan", vendedor.nombre)
        assertEquals("Pérez", vendedor.apellido)
        assertEquals("123456789", vendedor.telefono)
        assertEquals("juan.perez@example.com", vendedor.correo)
        assertEquals("Calle Falsa 123", vendedor.direccion)
        assertEquals("z001", vendedor.idzona)
    }

    @Test
    fun vendedoresConMismosValoresSonIguales() {
        val v1 = Vendedor("v002", 1, "87654321", "Ana", "López", "987654321", "ana@example.com", "Av. Siempre Viva 742", "z002")
        val v2 = Vendedor("v002", 1, "87654321", "Ana", "López", "987654321", "ana@example.com", "Av. Siempre Viva 742", "z002")

        assertEquals(v1, v2)
    }

    @Test
    fun copyModificaUnCampo() {
        val original = Vendedor("v003", 2, "11223344", "Luis", "Martínez", "111222333", "luis@example.com", "Calle 10", "z003")
        val copia = original.copy(telefono = "999888777")

        assertEquals("v003", copia.id)
        assertEquals("999888777", copia.telefono)
        assertEquals("Luis", copia.nombre)
    }

    @Test
    fun toStringContieneDatosEsperados() {
        val vendedor = Vendedor("v004", 3, "99887766", "Carla", "Gómez", "222333444", "carla@example.com", "Calle Luna", "z004")
        val str = vendedor.toString()

        assertTrue(str.contains("id=v004"))
        assertTrue(str.contains("idTipoDocumento=3"))
        assertTrue(str.contains("numeroDocumento=99887766"))
        assertTrue(str.contains("nombre=Carla"))
        assertTrue(str.contains("apellido=Gómez"))
        assertTrue(str.contains("telefono=222333444"))
        assertTrue(str.contains("correo=carla@example.com"))
        assertTrue(str.contains("direccion=Calle Luna"))
        assertTrue(str.contains("idzona=z004"))
    }
}
