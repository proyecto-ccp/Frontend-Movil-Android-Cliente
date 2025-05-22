package com.uxdesign.ccp_frontend

import org.junit.Assert.*
import org.junit.Test

class ZonaTest {

    @Test
    fun crearZonaCorrectamente() {
        val zona = Zona("z001", "c001", "Madrid", "Centro", "Norte-Sur-Este-Oeste")

        assertEquals("z001", zona.id)
        assertEquals("c001", zona.idCiudad)
        assertEquals("Madrid", zona.ciudad)
        assertEquals("Centro", zona.nombre)
        assertEquals("Norte-Sur-Este-Oeste", zona.limites)
    }

    @Test
    fun dosZonasConMismosValoresSonIguales() {
        val zona1 = Zona("z002", "c002", "Barcelona", "Eixample", "N-S-E-O")
        val zona2 = Zona("z002", "c002", "Barcelona", "Eixample", "N-S-E-O")

        assertEquals(zona1, zona2)
    }

    @Test
    fun copiarZonaCambiandoNombre() {
        val original = Zona("z003", "c003", "Valencia", "Norte", "lim1")
        val copia = original.copy(nombre = "Sur")

        assertEquals("z003", copia.id)
        assertEquals("c003", copia.idCiudad)
        assertEquals("Valencia", copia.ciudad)
        assertEquals("Sur", copia.nombre)
        assertEquals("lim1", copia.limites)
    }

    @Test
    fun toStringContieneValoresEsperados() {
        val zona = Zona("z004", "c004", "Sevilla", "Casco Antiguo", "Este-Oeste")
        val str = zona.toString()

        assertTrue(str.contains("id=z004"))
        assertTrue(str.contains("idCiudad=c004"))
        assertTrue(str.contains("ciudad=Sevilla"))
        assertTrue(str.contains("nombre=Casco Antiguo"))
        assertTrue(str.contains("limites=Este-Oeste"))
    }
}
