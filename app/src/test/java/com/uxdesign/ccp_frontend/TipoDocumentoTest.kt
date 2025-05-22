package com.uxdesign.ccp_frontend

import org.junit.Assert.*
import org.junit.Test

class TipoDocumentoTest {

    @Test
    fun crearTipoDocumentoCorrectamente() {
        val tipoDocumento = TipoDocumento(1, "DNI", "dni")

        assertEquals(1, tipoDocumento.id)
        assertEquals("DNI", tipoDocumento.nombre)
        assertEquals("dni", tipoDocumento.codigo)
    }

    @Test
    fun tiposDocumentoConMismosValoresSonIguales() {
        val tipo1 = TipoDocumento(2, "Pasaporte", "pass")
        val tipo2 = TipoDocumento(2, "Pasaporte", "pass")

        assertEquals(tipo1, tipo2)
    }

    @Test
    fun copyModificaCodigo() {
        val original = TipoDocumento(3, "Carnet de Extranjería", "ce")
        val copia = original.copy(codigo = "extranjero")

        assertEquals(3, copia.id)
        assertEquals("Carnet de Extranjería", copia.nombre)
        assertEquals("extranjero", copia.codigo)
    }

    @Test
    fun toStringContieneValoresEsperados() {
        val tipoDocumento = TipoDocumento(4, "Licencia", "lic")
        val str = tipoDocumento.toString()

        assertTrue(str.contains("id=4"))
        assertTrue(str.contains("nombre=Licencia"))
        assertTrue(str.contains("codigo=lic"))
    }
}
