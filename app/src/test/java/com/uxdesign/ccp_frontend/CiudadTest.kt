package com.uxdesign.ccp_frontend

import org.junit.Assert.*
import org.junit.Test

class CiudadTest {

    @Test
    fun crearCiudadCorrectamente() {
        val ciudad = Ciudad("001", "Madrid", 3000000)

        assertEquals("001", ciudad.id)
        assertEquals("Madrid", ciudad.nombre)
        assertEquals(3000000, ciudad.poblacion)
    }

    @Test
    fun dosCiudadesConMismosValoresSonIguales() {
        val ciudad1 = Ciudad("002", "Barcelona", 1600000)
        val ciudad2 = Ciudad("002", "Barcelona", 1600000)

        assertEquals(ciudad1, ciudad2)
    }

    @Test
    fun copyModificaSoloUnCampo() {
        val original = Ciudad("003", "Valencia", 800000)
        val copia = original.copy(nombre = "Sevilla")

        assertEquals("003", copia.id)
        assertEquals("Sevilla", copia.nombre)
        assertEquals(800000, copia.poblacion)
    }

    @Test
    fun toStringContieneValoresEsperados() {
        val ciudad = Ciudad("004", "Zaragoza", 675000)
        val str = ciudad.toString()

        assertTrue(str.contains("id=004"))
        assertTrue(str.contains("nombre=Zaragoza"))
        assertTrue(str.contains("poblacion=675000"))
    }
}
